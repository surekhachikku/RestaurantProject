import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RestaurantService } from '../../../../services/restaurant.service';
import { Restaurant } from '../../../../models/restaurant.model';

@Component({
  selector: 'app-delete-restaurant',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './delete-restaurant.component.html',
  styleUrls: ['./delete-restaurant.component.css']
})
export class DeleteRestaurantComponent implements OnInit {
  restaurants: Restaurant[] = [];
  message = '';

  editId: number | null = null;
  editRestaurant: Partial<Restaurant> = {};
  selectedFile?: File;

  constructor(private restaurantService: RestaurantService) {}

  ngOnInit() {
    this.loadRestaurants();
  }

  loadRestaurants() {
    this.restaurantService.getRestaurants().subscribe({
      next: (data) => this.restaurants = data,
      error: () => this.message = '⚠️ Failed to load restaurants.'
    });
  }

  startEdit(r: Restaurant) {
    this.editId = r.id!;
    this.editRestaurant = { ...r };
  }

  cancelEdit() {
    this.editId = null;
    this.editRestaurant = {};
    this.selectedFile = undefined;
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  updateRestaurant() {
    if (!this.editId) return;

    this.restaurantService.updateRestaurant(this.editId, this.editRestaurant, this.selectedFile)
      .subscribe({
        next: () => {
          this.message = '✅ Restaurant updated successfully!';
          this.editId = null;
          this.selectedFile = undefined;
          this.loadRestaurants();
        },
        error: (err) => {
          console.error(err);
          this.message = '❌ Failed to update restaurant.';
        }
      });
  }

  deleteRestaurant(id: number) {
    if (!confirm('Are you sure you want to delete this restaurant?')) return;

    this.restaurantService.deleteRestaurant(id).subscribe({
      next: () => {
        this.message = '🗑️ Restaurant deleted successfully!';
        this.loadRestaurants();
      },
      error: () => this.message = '❌ Failed to delete restaurant.'
    });
  }
}
