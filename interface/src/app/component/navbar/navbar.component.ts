import { Component, OnInit } from '@angular/core';
import { NavbarManager } from './NavbarManager';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  constructor(public navbarManager: NavbarManager) {
  }
}
