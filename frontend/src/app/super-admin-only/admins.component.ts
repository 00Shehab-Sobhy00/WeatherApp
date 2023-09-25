import { Component, OnInit } from '@angular/core';
import { AdminsService } from './admins.service';
import { AdminsDTO } from '../DTOs/AdminsDTO';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'admins',
  templateUrl: './admins.component.html',
  styleUrls: ['./admins.component.css']
})
export class AdminsComponent implements OnInit {
  error: string
  isAdmin = false;
  adminName: string;
  isEdit = false;
  searchAdmin: string;
  isExist = false;
  allAdmins: AdminsDTO[];
  newAdmin: AdminsDTO = {} as AdminsDTO;

  constructor(private service: AdminsService,
    public toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.getAllAdmins();
  }

  getAllAdmins() {
    this.service.getAllAdmins().subscribe(admins => {
      console.log(admins);
      this.allAdmins = admins;
      this.isAdmin = false;
      this.adminName = null;
      this.error = null;
    });
  }

  addAdmin() {
    this.service.addAdmin(this.newAdmin).subscribe(() => {
      this.isExist = true;
      this.getAllAdmins();
      this.newAdmin = {} as AdminsDTO;
    },
      error => {
        if (error.status === 409) {
          this.toastr.error("Email already exist");
          this.isEdit = true;
        }
      });

    this.getAllAdmins();
    this.newAdmin = {} as AdminsDTO;
    if (!this.isEdit)
      this.toastr.success("Admin has been added ");
  }

  searchAdminByName() {
    if (this.adminName) {
      console.log(this.adminName);

      this.service.getAdminByName(this.adminName).subscribe(
        (resp) => {
          console.log(resp);
          this.allAdmins = [resp];
          this.isAdmin = true;
          this.adminName = null;
          this.error = null;
        },
        (error) => {
          this.toastr.info(error.error.description);
        }
      );
    } else {
      this.getAllAdmins();
    }
  }


  updateAdmin(adminId: string, admin: AdminsDTO) {
    console.log("admin updateded name " + admin.name);
    this.service.updateAdminInfo(adminId, admin).subscribe(() => {
      this.getAllAdmins();
      this.toastr.success('Admin han been updated ');
    });
  }

  deleteAdmin(adminId: string) {
    this.service.deleteAdmin(adminId).subscribe(() => {
      this.getAllAdmins();
      this.toastr.success('Admin han been deleted ');
    });
  }
}


//explaination of  : type assertion, 
// as ProfileDetails syntax is called a type assertion,
//  and it tells the TypeScript compiler to treat the empty object literal 
//  {} as an instance of the ProfileDetails type.
//----------------------------------------------------------------------
