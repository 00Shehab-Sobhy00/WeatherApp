import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { WeatherNotes } from '../DTOs/WeatherNotesDTO';

@Injectable({
  providedIn: 'root'
})
export class NotesService {

  constructor(private http: HttpClient) { }

  private url = 'http://localhost:8080';



  getAllNotesForAdmin(adminId: number) {
    return this.http.get<WeatherNotes[]>(this.url + "/notes/" + adminId)
  }


  addNoteForAdmin(adminId: number, noteValue: WeatherNotes) {
    return this.http.post<WeatherNotes>(this.url + "/notes/" + adminId, noteValue)
  }


  updatedWeatherNote(noteId: number, updateNote: WeatherNotes) {
    return this.http.put<WeatherNotes>(this.url + "/notes/" + noteId, updateNote)
  }


  deleteWeatherNote(noteId: number) {
    return this.http.delete(this.url + "/notes/" + noteId, { responseType: 'text' })
  }


  // super admin notes 
  getAllNotesOnSystem() {
    return this.http.get<WeatherNotes[]>(this.url + "/super/notes")
  }

  seachForCityNotes(cityName: string) {
    return this.http.get<WeatherNotes[]>(this.url + "/super/" + cityName + "/notes")
  }


}
