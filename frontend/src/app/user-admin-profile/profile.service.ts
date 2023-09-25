import { Injectable } from '@angular/core'; 
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { take, exhaustMap, tap, map } from 'rxjs/operators';
import { ProfileDetails } from '../DTOs/ProfileDetails';
 
 

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private url = 'http://localhost:8080';

  constructor(private http: HttpClient  ) { }


  getUserProfileDetails(userId: string) {
        return this.http.get<ProfileDetails>(this.url + '/user/profile/' + userId) 

  }

  EditeUserProfileDetails( userId : string , userAdminDetails: ProfileDetails ) {

      return this.http.put<ProfileDetails>(this.url + '/user/profile/' + userId , userAdminDetails )
  }

  
  getAdminProfileDetails(userId: string) {
    return this.http.get<ProfileDetails>(this.url + '/admin/profile/' + userId) 

}

EditeAdminProfileDetails( userId : string , userAdminDetails: ProfileDetails ) {

  return this.http.put<ProfileDetails>(this.url + '/admin/profile/' + userId , userAdminDetails )
}



}


