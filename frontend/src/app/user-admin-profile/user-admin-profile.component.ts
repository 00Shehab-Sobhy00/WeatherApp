import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { ROLE } from 'src/app/DTOs/Roles';
import { ProfileService } from './profile.service';
import { ProfileDetails } from '../DTOs/ProfileDetails';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'user/profile',
  templateUrl: './user-admin-profile.component.html',
  styleUrls: ['./user-admin-profile.component.css']
})
export class UserAdminProfileComponent implements OnInit {
  userId: string;
  role : string
  userSub: Subscription;
  user: ProfileDetails = {
    name: '',
    email: '',
    password: '',
    phone: 0
  };
  isEdit: boolean = false;


  constructor(private service: ProfileService,
  public authService: AuthService,
  private toastr : ToastrService

  )  {}

  ngOnInit(): void {
  
    this.userSub = this.authService.user.subscribe(user => {
         this.userId = user.userId;
         console.log( "user id from profile  back "   + user.userId);
         
    
        this.role = user.userRole
        console.log(  "and this is the nw " + this.userId + "  with role " + user.userRole);
      
    }
    )

   if(this.role == 'USER'){
    this.service.getUserProfileDetails(this.userId).subscribe(params => {
      console.log(params);
      this.user.name = params.name;
      this.user.email = params.email;
      this.user.password = params.password;
      this.user.phone = params.phone;
    });
   }
   else if(this.role == 'ADMIN'){
    this.service.getAdminProfileDetails(this.userId).subscribe(params => {
      console.log(params);
      this.user.name = params.name;
      this.user.email = params.email;
      this.user.password = params.password;
      this.user.phone = params.phone;
    });
   }

  } 

    updateProfile() {
      if(this.role == 'ADMIN'){
        this.service.EditeAdminProfileDetails( this.userId , this.user ).subscribe(() => {
          this.isEdit = false;
        
        });
      }
      if(this.role == 'USER'){
        this.service.EditeUserProfileDetails( this.userId , this.user ).subscribe(() => {
          this.isEdit = false;
   
        });
      }
      // no way 
      else console.log("no user has been found ");
      
      this.toastr.success('Data han been updated ');
  }

 
  // updateUserProfile() {
  //   this.service.EditeUserProfileDetails( this.userId , this.user ).subscribe(() => {
  //     this.isEdit = false;
  //       this.toastr.success('Data han been updated ');
  //   });
  // }

  // updateAdminProfile() {
  //   this.service.EditeAdminProfileDetails( this.userId , this.user ).subscribe(() => {
  //     this.isEdit = false;
    
  //   });
  // }


}






