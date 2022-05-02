import { Component } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { NavbarManager } from '../NavbarManager';

@Component({
  selector: 'app-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.css']
})
export class UserMenuComponent {

  constructor(public navbarManager: NavbarManager, private userService: UserService) {}

  logOut(): void {
    let request = this.userService.logOut();
    request.callback = () => this.navbarManager.user = null;
    request.send();
  }
}
