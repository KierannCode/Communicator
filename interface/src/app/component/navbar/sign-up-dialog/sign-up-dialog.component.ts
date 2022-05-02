import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { SignUpData } from 'src/app/model/form_data/SignUpData';
import { UserService } from 'src/app/service/user.service';
import { Progress } from 'src/app/util/Progress';
import { NavbarManager } from '../NavbarManager';

@Component({
  selector: 'app-sign-up-dialog',
  templateUrl: './sign-up-dialog.component.html',
  styleUrls: ['./sign-up-dialog.component.css']
})
export class SignUpDialogComponent {
  signUpFormControl = {
    username: new FormControl(),
    rawPassword: new FormControl(),
    passwordConfirmation: new FormControl(),
    email: new FormControl(),
    description: new FormControl(),
    gender: new FormControl()
  };

  signUpData: SignUpData = {};

  signUpProgress = new Progress();

  constructor(private navbarManager: NavbarManager, private userService: UserService) { }

  submitSignUp(): void {
    this.signUpData.username = this.signUpFormControl.username.value;
    this.signUpData.rawPassword = this.signUpFormControl.rawPassword.value;
    this.signUpData.passwordConfirmation = this.signUpFormControl.passwordConfirmation.value;
    this.signUpData.email = this.signUpFormControl.email.value;
    this.signUpData.description = this.signUpFormControl.description.value;
    this.signUpData.gender = this.signUpFormControl.gender.value;

    let request = this.userService.signUp(this.signUpData);
    request.callback = user => {
      this.navbarManager.logInFormControl['username'].setValue(user.username);
      this.navbarManager.logInFormControl['rawPassword'].setValue('');
      this.navbarManager.logInMenuTrigger.openMenu();
      this.navbarManager.closeSignUpDialog();
    }
    request.formControl = this.signUpFormControl;
    request.progress = this.signUpProgress;
    request.successMessageBuilder = user => `Your account '${user.username}' was successfully created`;
    request.send();
  }
}
