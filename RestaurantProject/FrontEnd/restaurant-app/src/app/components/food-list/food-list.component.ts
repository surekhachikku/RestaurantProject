import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FoodService } from '../../services/food.service';
import { OrderService } from '../../services/order.service';
import { Food } from '../../models/food.model';
import { FoodResponse } from '../../models/food-response.model';
import { Order } from '../../models/order.model';
import { User } from '../../models/user.model';
// Correct import for jwt-decode
//import jwtDecode from 'jwt-decode';
import { jwtDecode } from 'jwt-decode';
interface MyJwtPayload {
  sub: string;       // username
  userId: number;    // numeric userId from JWT
  role?: string;
}

@Component({
  selector: 'app-food-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './food-list.component.html',
  styleUrls: ['./food-list.component.css']
})
export class FoodListComponent implements OnInit {
  restaurantId!: number;
  restaurantName: string = '';
  restaurantDetails: any;
  foodItems: Food[] = [];
  vegItems: Food[] = [];
  nonVegItems: Food[] = [];
  userId?: number;

  constructor(
    private route: ActivatedRoute,
    private foodService: FoodService,
    private orderService: OrderService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Decode JWT to get userId
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const decoded = jwtDecode<MyJwtPayload>(token);
        if (decoded.userId && !isNaN(decoded.userId)) {
          this.userId = decoded.userId;
        } else {
          console.error('Invalid userId in JWT:', decoded.userId);
        }
      } catch (err) {
        console.error('Failed to decode JWT', err);
      }
    } else {
      console.warn('No token found in localStorage');
    }

    // Get restaurantId from route
    const param = this.route.snapshot.paramMap.get('restaurantId');
    if (!param) return;
    this.restaurantId = Number(param);

    // Fetch food items from backend
    this.foodService.getFoodByRestaurantId(this.restaurantId).subscribe({
      next: (data: FoodResponse) => {
        this.restaurantDetails = data.restaurant;
        this.restaurantName = data.restaurant?.name ?? 'Restaurant';

        this.foodItems = data.foodItemList.map((f: Food) => ({
          ...f,
          selected: false,
          quantity: 1
        }));

        this.vegItems = this.foodItems.filter(f => f.isVeg === 'Veg');
        this.nonVegItems = this.foodItems.filter(f => f.isVeg === 'Non-Veg');
      },
      error: err => {
        console.error('Error fetching food items', err);
        this.foodItems = [];
        this.vegItems = [];
        this.nonVegItems = [];
      }
    });
  }

  calculateTotal(): number {
    return this.foodItems
      .filter(f => f.selected)
      .reduce((sum, f) => sum + f.price * f.quantity, 0);
  }

  toggleSelection(food: Food): void {
    food.selected = !food.selected;
  }

  placeOrder(): void {
    if (!this.userId || this.userId <= 0) {
      alert('Invalid user. Please login again.');
      return;
    }

    const selectedFoods = this.foodItems.filter(f => f.selected);
    if (selectedFoods.length === 0) {
      alert('Please select at least one food item');
      return;
    }

    const order: Order = {
      foodItems: selectedFoods,
      restaurant: this.restaurantDetails,
      totalPrice: selectedFoods.reduce((sum, f) => sum + f.price * f.quantity, 0),
      user: { id: this.userId } as User
    };

    this.orderService.placeOrder(order).subscribe({
      next: (response) => {
        console.log('Order placed successfully:', response);
        this.router.navigate(['/order-success'], { state: { order } });
      },
      error: err => {
        console.error('Error placing order:', err);
        alert('Failed to place order');
      }
    });
  }

  back(): void {
    this.router.navigate(['/']);
  }
}
