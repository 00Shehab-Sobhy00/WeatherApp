import { HttpBackend, HttpHandler, HttpHeaders, HttpInterceptor, HttpParams, HttpRequest } from "@angular/common/http";
import { AuthService } from "./auth.service";
import { Injectable } from "@angular/core";
import { take, exhaustMap, tap, map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

    constructor(private authService: AuthService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler) {
        return this.authService.user.pipe(
            take(1),
            exhaustMap(theUser => {

                if(!theUser)
                    return next.handle(req);
            
                const headers = new HttpHeaders({
                    'Authorization': `Bearer ${theUser.token}`
                });
                
              const  modifiedRequest = req.clone({
                headers : headers 
            
            })
                
                return next.handle(modifiedRequest);
            })
        )
    }


}




 // 1- take operator to take instade of using subscribe  then .unsubscribe
    // 2- exhaustMap wait for the fist observiable (user) to complite +
    //  get the data from previous observiable +
    // return new observiable which then replace our previose observiable in intire observiable chane [return observiable : i think]
    // this example : we start by user observiable -> get() observiable , get is been replaced by user and is been returned 
    // return this.authService.user.pipe(
    //   take(1),
    //   exhaustMap(theUser => {
    //     console.log(theUser.token);
    //     // const headers = new HttpHeaders().set('Authorization', 'Bearer ' + theUser.token);
    //     // console.log(headers);


    //     const headers = new HttpHeaders({
    //       'Authorization': `Bearer ${theUser.token}`
    //     });

    //     const options = { headers: headers };

        // this.http.get('http://localhost:8080/your-api-endpoint', options)
        //   .subscribe(response => {
        //     // Handle the response from the backend
        //   }, error => {
        //     // Handle any errors
        //   });
// console.log(this.url + '/user/profile/' + userId, options);