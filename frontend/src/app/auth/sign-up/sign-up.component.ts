import { Component, OnInit } from '@angular/core';
// import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CustomValidators } from '../All-Validators';
import { AuthService } from '../auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormControl, Validators, FormBuilder }  from '@angular/forms';
import {FormsModule,ReactiveFormsModule} from '@angular/forms';
@Component({
  selector: 'sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
 
  signupForm: FormGroup;
 
  invValidSignUp = false;
    userId : string
 

  constructor( private service : AuthService ,
    private router: Router,
    private route: ActivatedRoute ) {
  }

  // constructor(public dialog  : MatDialog) {
  // }

//pop up for deleting task
// openDialog(): void {
//   this.dialog.open(SubmitPromtComponent,{
//     data:{
//     form : this.signupForm
//     }
//   });
// }

  ngOnInit() {
    this.signupForm = new FormGroup({

      'name': new FormControl('', [
        Validators.required,
        Validators.pattern("[a-zA-Z ]*"),
        Validators.minLength(3),
        Validators.maxLength(10),
        //  CustomValidators.cannotContainSpace
      ]),
      'email': new FormControl(null, [Validators.required, Validators.email]),
        'password' : new FormControl('', Validators.compose([
          Validators.required,
          CustomValidators.patternValidator(/\d/, { atLeastOneNumber: true }),
          // CustomValidators.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
          //   CustomValidators.patternValidator( /.{8,}/, {minLength : true}),          //   Validators.minLength(8), ??? 
          //   CustomValidators.patternValidator( /^.{1,20}$/, {maxLength : true}),        //    Validators.maxLength(20),  ???
          //   CustomValidators.patternValidator(/^[^\s]+$/, { containSpace: true }),
        ])),

        'phone': new FormControl('', [Validators.required ,  Validators.pattern('[- +()0-9]+')]),

  
    });
  }

  // get all form controls  : section
  get userName() {
    return this.signupForm.get('name');
  }
  get Email() {
    return this.signupForm.get('email');
  }
  get Password() {
    return this.signupForm.get('password');
  }

  get phoneNumber() {
    return this.signupForm.get('phone');
  }
 

  // fuctions req in order to modifiy my datas


  
//updated
onSubmit() {
  console.log(this.signupForm);

this.service.signUp(this.signupForm.value)

.subscribe(response => {
  this.userId = response.userId
  console.log(this.userId  + " user id from login component " );
  if(response){
    this.navigateToHome()

  }
    
  else
   this.invValidSignUp = true;
//  this.signupForm.reset();
  } )  


// this.route.paramMap.subscribe(
//   params => {
//     this.userId =   +params.get('userId');
   
// console.log(" this is user id " + this.userId);

//   }
// )



}


  navigateToHome() {
    this.router.navigate([''], {
      queryParams :{
        userId : this.userId
      }
    })
    // { relativeTo: this.route }
  }

}
