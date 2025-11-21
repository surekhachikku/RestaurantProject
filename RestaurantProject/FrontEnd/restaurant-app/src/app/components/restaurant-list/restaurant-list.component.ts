import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { RestaurantService } from '../../services/restaurant.service';
import { Restaurant } from '../../models/restaurant.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-restaurant-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './restaurant-list.component.html',
  styleUrls: ['./restaurant-list.component.css']
})
export class RestaurantListComponent implements OnInit {
  restaurants: Restaurant[] = [];

  constructor(
    private restaurantService: RestaurantService,
    private router: Router,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.restaurantService.getRestaurants().subscribe({
      next: data => this.restaurants = data,
      error: err => {
        console.error('Error fetching restaurants', err);

        // ⭐ If backend returns 401, force login again
        if (err.status === 401 || err.status === 403) {
          this.auth.logout();
          this.router.navigate(['/login'], {
            queryParams: { redirect: 'restaurants' }
          });
        }
      }
    });
  }

  viewFood(restaurantId: number): void {
    this.router.navigate(['/foods', restaurantId]);
  }
}
