import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnDestroy, OnInit } from '@angular/core';

import { Subscription } from 'rxjs';
import { AuthService } from './auth/auth.service';
import { ROLE } from './DTOs/Roles';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  constructor(public authService: AuthService, private route: ActivatedRoute, private router: Router) { }

  isAuthenticated = false;
  userSub: Subscription;

  userId: string;
  role: string
  ngOnInit() {
    this.authService.autoLogin();
    this.userSub = this.authService.user.subscribe(user => {
      if (!user) {
        this.isAuthenticated = false
      } else {
        this.isAuthenticated = true
        this.userId = user.userId
        console.log(user.userRole);
        
        this.role = user.userRole
      }
    }


    );

console.log(" hi my role is "  + this.role);
 

  }
 

  navigateToUserProfile(): void {
    this.router.navigate(['/profile', this.userId]);
  }
 

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
    this.authService.logout();
  }

  resetRole(){

    this.role = null;
  }
}
