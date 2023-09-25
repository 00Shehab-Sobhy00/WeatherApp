import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    Router,
    UrlTree
  } from '@angular/router';
  import { Injectable } from '@angular/core';
  import { Observable } from 'rxjs';
  import { map, tap, take } from 'rxjs/operators';
  
  import { AuthService } from '../auth.service';

  
  @Injectable({ providedIn: 'root' })
  export class AdminAuthNotesGuard implements CanActivate {
 
    constructor(private authService: AuthService, private router: Router) {}
  
    canActivate(route: ActivatedRouteSnapshot, router: RouterStateSnapshot )
    : boolean |UrlTree| Promise<boolean |UrlTree>| Observable<boolean |UrlTree> {
      return this.authService.user.pipe(
        take(1),
        map(user => {
            // thush value [object isnt null ] to true || falsh value [null or undefined ] to false 
          const isAuth = !!user;
          if (isAuth &&  (user.userRole == 'ADMIN' || user.userRole == 'SUPER_ADMIN' )) {
            console.log("hi only  admin  " + user.userRole +"!");
            
            return true;
          }
            return this.router.createUrlTree(['/permission-denied']);
      
            // const hasPermission = this.permissions.some((permission) => this.authService.hasPermission(permission));

            // if (hasPermission) {
            //   return true; // User has permission, allow access
       
        })
     
      );
    }
  }
  