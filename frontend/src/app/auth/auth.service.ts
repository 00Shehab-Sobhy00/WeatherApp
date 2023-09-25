import { AuthResponse } from '../DTOs/AuthResponse';
import { HttpClient  } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SignUpDTO } from '../DTOs/SignUpDTO';
import { catchError, map, tap } from 'rxjs/operators';
import {  BehaviorSubject, Subject, throwError } from 'rxjs';
import { LogInDTO } from '../DTOs/LogInDTO';
// import * as jwt from 'jsonwebtoken';
import jwt_decode from 'jwt-decode';
import { Router } from '@angular/router';
import { AuthResponseModel } from '../Models/AuthResponseModel';

 
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // this user response object iam creating to be able to stack trace whatever 
  // he is gonna do on my app based on the info that i have made 
  private url = 'http://localhost:8080'

// is a subject we can subscribe and get information when ever new data is emitted
  // user  = new Subject <AuthResponseModel>()
//--------------------------------------------------------------
// same as subscribe but the diff is BehaviorSubject give subscribers immediate acces to previously immediate value 
// even if they havent subscribe at the point of time 
  user  = new BehaviorSubject <AuthResponseModel>(null);
 private tokenExpirationTimer : any;


  constructor(private http: HttpClient , private router: Router ) { }

  signUp(signUpRequest: SignUpDTO) {
    return this.http.post<AuthResponse>(this.url + "/register", signUpRequest )   
      .pipe(
      
        // //  TODO ERROR HANDLING 
        catchError(errorRes => {
          return throwError(errorRes);
        }),
       
        //tap : it allows us to perform some action without changing the response  
       tap(resData =>{
        this.handleAuthentication (resData.token , resData.userId);
          
        }
     )
        
      )

  }

  logIn(logInRequest: LogInDTO)   {
    // return this.http.post<any>(this.url + "/login", logInRequest,  iam putting the token by default into interceptor)
    return this.http.post<any>(this.url + "/login", logInRequest)
      .pipe (
        // token + userId + role
        map((responseData => {
          return responseData;

        })),
        
        tap(resData =>{
          this.handleAuthentication (resData.token , resData.userId);
            
          }
      )  )
  }
  autoLogin(){
    // snap shot data we retriving from local storage 
    const userData :{
      email:  string,
      userId: string,
      _token : string,
      role :  string ,
      tokenExpirationDate : string
    } = JSON.parse(localStorage.getItem('userData'));
    if(!userData){
      return;
    }
    const loadedUser = new AuthResponseModel(
      userData.email , 
      userData.userId,
      userData.role,
       userData._token, 
      new Date(userData.tokenExpirationDate));

      // in model we check 
       console.log(  "this is loaded user from auto login "  +  loadedUser.token + "and my role is " + loadedUser.userRole);
      
      if (loadedUser.token){
        this.user.next(loadedUser);
          const expirationDuration = new Date (userData.tokenExpirationDate).getTime() - new Date().getTime();
          console.log("expirationDuration = " +  expirationDuration );
        
       this.autoLogout(expirationDuration)
      }
  } 

  logout(){
   this.user.next(null);
  //  localStorage.clear();
localStorage.removeItem('userData')
   this.router.navigate(['/login'], { queryParams: { userId: null }, queryParamsHandling: 'merge' });

   if(this.tokenExpirationTimer){
    clearTimeout(this.tokenExpirationTimer);
   }
   this.tokenExpirationTimer = null;
  }

  autoLogout(expirationDuration : number ){
    console.log(expirationDuration + "  ms" );
 this.tokenExpirationTimer =  setTimeout(()=>{
if   (expirationDuration == 0)
       this.logout();
  // } , 2000) for simulation
} , expirationDuration); 
  }

  // extracting the token and handle authentication that do everything we need  , also 
  private handleAuthentication(token: string, userId: string) {
    try {
      const decodedToken: { sub: string, exp: number, roles: string[] } = jwt_decode(token);
      const  tokenExDate  = new Date(decodedToken.exp *1000 );
      const email = decodedToken.sub;
      const role = decodedToken.roles[0];  
      // console.log("User with token " + token + " and email " + email + " with role " + role + " " + "ex date : " + tokenExDate );
   console.log( " with role " + role);

         // constructing the user with the data w get back from token 
         const user = new AuthResponseModel(email,userId ,role ,token,tokenExDate);
         this.user.next(user);   //  BehaviorSubject  // -- we useing the subject 
        //  to emitte this now as a current user loging in our ablication
      localStorage.setItem('userData',JSON.stringify(user) );  //token
       this.autoLogout( decodedToken.exp *1000 *1000  )
      return decodedToken;
    } catch (error) {
      console.error('Error decoding JWT token:', error);
      return null;
    } 
  }
}



        
