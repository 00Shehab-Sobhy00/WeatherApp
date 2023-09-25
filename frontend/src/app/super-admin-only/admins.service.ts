// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { ProfileDetails } from '../DTOs/ProfileDetails';

// @Injectable({
//   providedIn: 'root'
// })
// export class AdminsService {
//   private url = 'http://localhost:8080/weather';

//   constructor(private http: HttpClient) { }

//   getAllAdmins() {
//     return this.http.get<ProfileDetails[]>(`${this.url}/admins`);
//   }

//   addAdmin(admin: ProfileDetails) {
//     return this.http.post<ProfileDetails>(`${this.url}/admins`, admin);
//   }

//   updateAdmin(adminId: string, updatedAdmin: ProfileDetails) {
//     return this.http.put<ProfileDetails>(`${this.url}/admins/${adminId}`, updatedAdmin);
//   }

//   deleteAdmin(adminId: string) {
//     return this.http.delete(`${this.url}/admins/${adminId}`);
//   }
// }


import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdminsDTO } from '../DTOs/AdminsDTO';

@Injectable({
  providedIn: 'root'
})
export class AdminsService {
  private url = 'http://localhost:8080';

  constructor(private http: HttpClient) { }


  getAllAdmins() {
    return this.http.get<AdminsDTO[]>(this.url + "/admins")

  }

  getAdminByName(adminName: string) {
    return this.http.get<AdminsDTO>(this.url + "/admins/" + adminName)
  }

  addAdmin(admin: AdminsDTO) {
    console.log(admin);

    return this.http.post<AdminsDTO>(this.url + "/admins", admin)
  }


  updateAdminInfo(adminId: string, updatedAdmin: AdminsDTO) {
    console.log(this.url + "/admins/" + adminId, updatedAdmin);
    
    return this.http.put<AdminsDTO>(this.url + "/admins/" + adminId, updatedAdmin);
  }

  deleteAdmin(adminId: string) {
    return this.http.delete(this.url + "/admins/" + adminId, { responseType: 'text' })
  }





}

