import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(public auth: AuthService, private router: Router) {}

  goLogin() {
    this.router.navigate(['/login']);
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  // Helper to check if user is a normal user (logged in but not admin)
  isUser(): boolean {
    return this.auth.isLoggedIn() && !this.auth.isAdmin();
  }
}
