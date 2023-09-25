import { AbstractControl   , ValidationErrors, ValidatorFn } from "@angular/forms";

export class CustomValidators {
  // AbstractControl : from-control && form-group inherite from it 


  //setErrors() Manually set the errors for a control > params emitEvent: When true or not supplied 
  // (the default), the statusChanges observable emits an event after the errors are set.
  
  static patternValidator(regex: RegExp, error: ValidationErrors): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      if (!control.value) {
        // if control is empty return no error
        return null!;
      }
  
      // test the value of the control against the regexp supplied
      const valid = regex.test(control.value);
  
      // if true, return no error (no error), else return error passed in the second parameter
      return valid ? null! : error;
    };
  }
}