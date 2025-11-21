import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-restaurant-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './restaurant-details.component.html',
  styleUrl: './restaurant-details.component.css'
})
export class RestaurantDetailsComponent {

  constructor(private router: Router){}

  goToAddRestaurant(){
    this.router.navigate(['/admin/restaurant-details/add-restaurant']);
  }
  goToDeleteRestaurant(){
    this.router.navigate(['/admin/restaurant-details/delete-restaurant']);
  }
  goToAddFood(){
    this.router.navigate(['/admin/restaurant-details/add-food']);
  }  
}
