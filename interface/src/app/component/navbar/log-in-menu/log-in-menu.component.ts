import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatMenuTrigger } from '@angular/material/menu';
import { LogInData } from 'src/app/model/form_data/LogInData';
import { UserService } from 'src/app/service/user.service';
import { Progress } from 'src/app/util/Progress';
import { NavbarManager } from '../NavbarManager';

@Component({
  selector: 'app-log-in-menu',
  templateUrl: './log-in-menu.component.html',
  styleUrls: ['./log-in-menu.component.css']
})
export class LogInMenuComponent implements AfterViewInit {
  logInFormControl = { username: new FormControl(), rawPassword: new FormControl() };

  logInProgress = new Progress();

  @ViewChild(MatMenuTrigger) logInMenuTrigger!: MatMenuTrigger;

  constructor(public navbarManager: NavbarManager, private userService: UserService) {
  }

  ngAfterViewInit(): void {
    this.navbarManager.logInMenuTrigger = this.logInMenuTrigger;
    this.navbarManager.logInFormControl = this.logInFormControl;
  }

  submitLogIn(): void {
    let logInData: LogInData = {};
    logInData.username = this.logInFormControl.username.value;
    logInData.rawPassword = this.logInFormControl.rawPassword.value;

    let request = this.userService.logIn(logInData);
    request.callback = user => {
      this.logInMenuTrigger.closeMenu();
      this.navbarManager.user = user;
    }
    request.formControl = this.logInFormControl;
    request.progress = this.logInProgress;
    request.send();
  }

  openSignUpModal(): void {
    this.logInMenuTrigger.closeMenu();
    this.navbarManager.openSignUpDialog();
  }
}
