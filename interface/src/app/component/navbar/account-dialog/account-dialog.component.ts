import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { UserUpdateData } from 'src/app/model/form_data/UserUpdateData';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/service/user.service';
import { Progress } from 'src/app/util/Progress';
import { NavbarManager } from '../NavbarManager';

@Component({
  selector: 'app-account-dialog',
  templateUrl: './account-dialog.component.html',
  styleUrls: ['./account-dialog.component.css']
})
export class AccountDialogComponent {
  userUpdateFormControl: { [key: string]: FormControl } = {
    username: new FormControl(),
    rawPassword: new FormControl(),
    passwordConfirmation: new FormControl(),
    email: new FormControl(),
    description: new FormControl(),
    gender: new FormControl()
  };

  userUpdateData: UserUpdateData = { updatedFields: new Array<string>() };

  userUpdateProgress = new Progress();

  constructor(public navbarManager: NavbarManager, private userService: UserService) {
  }

  submitUserUpdate(): void {
  }

  allowUpdate(field: string) {
    this.userUpdateFormControl[field].setValue(this.navbarManager.user![field as keyof User]);
    this.userUpdateData.updatedFields?.push(field);
  }

  discardUpdate(field: string) {
    this.userUpdateData.updatedFields?.splice(this.userUpdateData.updatedFields.indexOf(field), 1);
    this.userUpdateFormControl[field].setValue(null);
  }

  submitUpdate(): void {
    if (this.userUpdateFormControl['username'].value != this.navbarManager.user?.username) {
      this.userUpdateData.username = this.userUpdateFormControl['username'].value;
    } else {
      this.userUpdateData.updatedFields?.splice(this.userUpdateData.updatedFields.indexOf('username'), 1);
    }
    this.userUpdateData.rawPassword = this.userUpdateFormControl['rawPassword'].value;
    if (this.userUpdateData.updatedFields?.includes('rawPassword')) {
      this.userUpdateData.passwordConfirmation = this.userUpdateFormControl['passwordConfirmation'].value;
    }
    this.userUpdateData.email = this.userUpdateFormControl['email'].value;
    this.userUpdateData.description = this.userUpdateFormControl['description'].value;
    this.userUpdateData.gender = this.userUpdateFormControl['gender'].value;

    let request = this.userService.updateUser(this.userUpdateData);
    request.callback = user => {
      this.navbarManager.user = user;
      this.navbarManager.closeAccountDialog();
    }
    request.formControl = this.userUpdateFormControl;
    request.progress = this.userUpdateProgress;
    request.successMessageBuilder = () => `Your informations were successfully updated`;
    request.send();
  }
}
