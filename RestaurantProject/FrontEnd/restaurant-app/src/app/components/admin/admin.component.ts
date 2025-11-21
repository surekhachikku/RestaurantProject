import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {

  constructor(private router: Router){}

  goToAddRestaurant(){
    this.router.navigate(['/admin/restaurant-details']);
  }

  goToOrders(){
    this.router.navigate(['/admin/orders']);
  }
}
