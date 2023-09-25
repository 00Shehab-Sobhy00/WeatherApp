import { WeatherNotes } from './../DTOs/WeatherNotesDTO';
import { Component, OnInit } from '@angular/core';
import { NotesService } from './notes.service';
import { AuthService } from '../auth/auth.service';
import { Subscription } from 'rxjs';
import { delay } from 'rxjs-compat/operator/delay';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'notes',
  templateUrl: './notes.component.html',
  styleUrls: ['./notes.component.css']
})
export class NotesComponent implements OnInit {
  isEdit = false;
  isNotesForCity = false;
  
  adminSub: Subscription;
  adminId: number
  role: string
  nameOfCity: string

  notesList: WeatherNotes[] = []
  theNote: WeatherNotes = {
    cityName: '', note: '',
    id: 0,
    date: '',
    adminName: '',
    user: undefined
  };

  constructor(private authService: AuthService,
    private service: NotesService,
    public toastr : ToastrService
  ) { }


  ngOnInit(): void {
    this.adminSub = this.authService.user.subscribe(admin => {
      this.adminId = +admin.userId
      this.role = admin.userRole

    })
    this.checkRole()

  }

  getAdminNotes() {
    this.service.getAllNotesForAdmin(this.adminId).subscribe(
      notes => {
        console.log(notes);
        this.notesList = notes
        
        this.defaultNotes()
        console.log("notes list output " + this.notesList);

      }
    )

  }

  addNote() {
    this.service.addNoteForAdmin(this.adminId, this.theNote).subscribe(() => {
      this.theNote = {
        cityName: '', note: '',
        id: 0,
        date: '',
        adminName: '',
        user: undefined
      };
      this.checkRole()
      this.toastr.success(" Note has been added ");
    })

  }


  updateWeatherNote(noteId: number, updatedNote: WeatherNotes) {
    this.service.updatedWeatherNote(noteId, updatedNote).subscribe(() => {
      this.checkRole()
      this.toastr.success(" Note has been updated ");
    })

  }

  deleteWeatherNote(noteId: number) {
    this.service.deleteWeatherNote(noteId).subscribe(() => {
      this.checkRole()
      this.toastr.success(" Note has been deleted ");

    })


  }

  checkRole() {
    if (this.role == 'ADMIN') {
      this.getAdminNotes()
      console.log(" only admin is note page ..");
    } else if (this.role == 'SUPER_ADMIN') {
      console.log(" super admin is note page ..");
      this.getAllSystemNotes()
    }

  }

  // super admin notes 
  getAllSystemNotes() {
    this.service.getAllNotesOnSystem().subscribe(allNotes => {
      this.notesList = allNotes
      this.defaultNotes()
    })
  }

  getNotesForCity() {
    this.service.seachForCityNotes(this.nameOfCity).subscribe((notesRes) => {
    if(notesRes.length == 0 &&this.nameOfCity != null){
      this.toastr.info(" Could not find city matchs your search..");
    }else
      this.notesList = notesRes

    })
  }

  defaultNotes() {
    for(var n of this.notesList){
      if(n.adminName==null)
        n.adminName = "Default Note"
      
    }
 
   
  }

}

