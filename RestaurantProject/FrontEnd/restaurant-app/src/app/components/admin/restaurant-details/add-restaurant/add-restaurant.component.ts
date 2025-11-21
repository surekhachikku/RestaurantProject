

import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-restaurant',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-restaurant.component.html',
  styleUrls: ['./add-restaurant.component.css']
})
export class AddRestaurantComponent {
  restaurant = {
    name: '',
    address: '',
    city: '',
    restaurantDescription: ''
  };

  // 🟢 Declare all needed properties
  selectedFile?: File;
  previewUrl: string | ArrayBuffer | null = null;
  deleteId: number | null = null;
  message = '';

 // private baseUrl = 'http://localhost:9091/restaurant';
 private baseUrl = '/restaurant'; // ✅ use relative path so it goes through the API Gateway


  constructor(private http: HttpClient) {}

  // 🖼️ File selection + preview
  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];

    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = (e: ProgressEvent<FileReader>) => {
        this.previewUrl = e.target?.result ?? null; // ✅ ensures no undefined type
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  // ➕ Add restaurant
  onSubmit() {
    if (!this.selectedFile) {
      this.message = '⚠️ Please select an image.';
      return;
    }

    const formData = new FormData();
    formData.append('name', this.restaurant.name);
    formData.append('address', this.restaurant.address);
    formData.append('city', this.restaurant.city);
    formData.append('restaurantDescription', this.restaurant.restaurantDescription);
    formData.append('image', this.selectedFile);

    this.http.post(`${this.baseUrl}/addRestaurantsImage`, formData).subscribe({
      next: (res) => {
        console.log('✅ Restaurant added:', res);
        this.message = '✅ Restaurant added successfully!';
        this.resetForm();
      },
      error: (err) => {
        console.error('❌ Error adding restaurant:', err);
        this.message = '❌ Failed to add restaurant.';
      }
    });
  }

  // 🗑️ Delete restaurant by ID
  deleteRestaurant() {
    if (!this.deleteId) {
      this.message = '⚠️ Please enter a restaurant ID.';
      return;
    }

    this.http.delete(`${this.baseUrl}/delete/${this.deleteId}`).subscribe({
      next: () => {
        this.message = `🗑️ Restaurant with ID ${this.deleteId} deleted successfully!`;
        this.deleteId = null;
      },
      error: (err) => {
        console.error('❌ Error deleting restaurant:', err);
        this.message = '❌ Failed to delete restaurant. Please check the ID.';
      }
    });
  }

  // 🔁 Reset form after add
  resetForm() {
    this.restaurant = {
      name: '',
      address: '',
      city: '',
      restaurantDescription: ''
    };
    this.selectedFile = undefined;
    this.previewUrl = null;
  }
}
