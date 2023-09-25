import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CustomValidators } from '../All-Validators';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from '../auth.service';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  signupForm!: FormGroup;

  invValidLogin : boolean;
  // jwtToken : string


 

  constructor(private service : AuthService,
    // private router: Router   // opstioncal params 
    private router: Router,
    private route: ActivatedRoute
    ) {
  }

 

  ngOnInit() {
    this.signupForm = new FormGroup({

      'email': new FormControl(null, [Validators.required, Validators.email]),
        'password' : new FormControl('', Validators.compose([
          Validators.required,
          // CustomValidators.patternValidator(/\d/, { atLeastOneNumber: true }),
          // CustomValidators.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
          //   CustomValidators.patternValidator( /.{8,}/, {minLength : true}),          //   Validators.minLength(8), ??? 
          //   CustomValidators.patternValidator( /^.{1,20}$/, {maxLength : true}),        //    Validators.maxLength(20),  ???
          //   CustomValidators.patternValidator(/^[^\s]+$/, { containSpace: true }),
        ])),

    });


  }

  get Email() {
    return this.signupForm.get('email');
  }
  get Password() {
    return this.signupForm.get('password');
  }


 
 
  onSubmit() {
    console.log(this.signupForm);
    this.service.logIn(this.signupForm.value).subscribe(
      response => {
      
        this.invValidLogin = false;
        this.signupForm.reset();
        this.navigateToHome(response.userId);
      },
      error => {
        this.invValidLogin = true;
      }
    );
  }



  navigateToHome(userID : string ) {
    this.router.navigate([''], {
      queryParams :{
        userId : userID
      }
    })
 
  }


  
}


// [routerLink]="['user/','profile/', userId]" 

    // console.log(response);
    // this.jwtToken = response.token
    // this.userId = response.userId

    // localStorage.setItem('JWT',this.jwtToken);


    
  
   