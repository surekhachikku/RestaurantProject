import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FoodResponse } from '../models/food-response.model';
import { Food } from '../models/food.model';

@Injectable({ providedIn: 'root' })
export class FoodService {

  // Correct base URL through proxy → API Gateway
  private baseUrl = '/food';

  constructor(private http: HttpClient) {}

  // Fetch food by restaurant ID
  getFoodByRestaurantId(restaurantId: number): Observable<FoodResponse> {
    return this.http.get<FoodResponse>(
      `${this.baseUrl}/fetchFoodByRestaurantId/${restaurantId}`
    );
  }

  // Add food
  addFood(foodItem: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/addFood`, foodItem);
  }

  // Update food
  updateFood(food: Food): Observable<Food> {
    return this.http.put<Food>(
      `${this.baseUrl}/updateFood/${food.id}`,
      food
    );
  }

  // Delete food
  deleteFood(foodId: number): Observable<void> {
    return this.http.delete<void>(
      `${this.baseUrl}/deleteFood/${foodId}`
    );
  }
}
