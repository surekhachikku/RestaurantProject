import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Restaurant } from '../models/restaurant.model';

@Injectable({ providedIn: 'root' })
export class RestaurantService {

  // API Gateway base URL (proxied automatically)
  private baseUrl = '/restaurant';

  constructor(private http: HttpClient) {}

  // ✔ Fetch all restaurants
  getRestaurants(): Observable<Restaurant[]> {
    return this.http.get<Restaurant[]>(`${this.baseUrl}/fetchRestaurants`);
  }

  // ✔ Add restaurant with image
  addRestaurant(restaurant: Restaurant): Observable<Restaurant> {
    return this.http.post<Restaurant>(
      `${this.baseUrl}/addRestaurantsImage`,
      restaurant
    );
  }

  // ✔ Update restaurant (with optional image)
  updateRestaurant(id: number, restaurant: Partial<Restaurant>, imageFile?: File) {
    const formData = new FormData();

    formData.append('name', restaurant.name || '');
    formData.append('address', restaurant.address || '');
    formData.append('city', restaurant.city || '');
    formData.append('restaurantDescription', restaurant.restaurantDescription || '');

    if (imageFile) {
      formData.append('image', imageFile);
    }

    return this.http.put(`${this.baseUrl}/updateWithImage/${id}`, formData);
  }

  // ✔ Delete restaurant
  deleteRestaurant(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/deleteRestaurant/${id}`);
  }
}
