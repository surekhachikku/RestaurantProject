import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  constructor(public auth: AuthService, private router: Router) {}

  goLogin() {
    this.router.navigate(['/login']);
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/']);
  }
}
