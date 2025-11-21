import { Component, OnInit } from '@angular/core';
import { FoodService } from '../../../../services/food.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RestaurantService } from '../../../../services/restaurant.service';
import { Restaurant } from '../../../../models/restaurant.model';
import { Food } from '../../../../models/food.model';

@Component({
  selector: 'app-add-food',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './add-food.component.html',
  styleUrls: ['./add-food.component.css']
})
export class AddFoodComponent implements OnInit {
  restaurants: Restaurant[] = [];
  selectedRestaurantId!: number | null;
  selectedRestaurantName: string = '';

  foodList: Food[] = [];
  newFood: Food = {
    id: 0,
    itemName: '',
    isVeg: 'Veg', // default
    price: 0,
    restaurantId: 0,
    quantity: 1,
    itemDescription: ''
  };

  editId: number | null = null;
  editFood: Food = { ...this.newFood };

  message = '';

  constructor(
    private restaurantService: RestaurantService,
    private foodService: FoodService
  ) {}

  ngOnInit(): void {
    this.fetchRestaurants();
  }

  fetchRestaurants() {
    this.restaurantService.getRestaurants().subscribe(res => {
      this.restaurants = res;
    });
  }

  onRestaurantSelect() {
    if (!this.selectedRestaurantId) {
      this.foodList = [];
      this.selectedRestaurantName = '';
      return;
    }

    // Find restaurant name
    const selectedRestaurant = this.restaurants.find(
      r => r.id === this.selectedRestaurantId
    );
    this.selectedRestaurantName = selectedRestaurant ? selectedRestaurant.name : '';

    // Fetch food items
    this.foodService.getFoodByRestaurantId(this.selectedRestaurantId).subscribe({
      next: (res: any) => {
        if (res && res.foodItemList && Array.isArray(res.foodItemList)) {
          this.foodList = res.foodItemList.map((item: any) => ({
            id: item.id,
            itemName: item.itemName || '',
            itemDescription: item.itemDescription || item.description || '',
            isVeg: item.isVeg || 'Veg',
            price: item.price || 0,
            restaurantId: item.restaurantId || this.selectedRestaurantId!,
            quantity: item.quantity || 1,
            selected: item.selected || false
          }));
        } else {
          this.foodList = [];
        }

        this.newFood.restaurantId = this.selectedRestaurantId!;
      },
      error: (err) => {
        console.error('Error fetching food:', err);
        this.foodList = [];
      }
    });
  }

  addFood() {
    if (!this.selectedRestaurantId) return;

    this.newFood.restaurantId = this.selectedRestaurantId;

    this.foodService.addFood(this.newFood).subscribe(() => {
      this.message = 'Food added successfully!';
      this.onRestaurantSelect();
      this.newFood = {
        id: 0,
        itemName: '',
        isVeg: 'Veg',
        price: 0,
        restaurantId: this.selectedRestaurantId!,
        quantity: 1,
        itemDescription: ''
      };
    });
  }

  startEdit(food: Food) {
    this.editId = food.id;
    this.editFood = { ...food };
  }

  cancelEdit() {
    this.editId = null;
  }

  updateFood() {
    this.foodService.updateFood(this.editFood).subscribe(() => {
      this.message = 'Food updated successfully!';
      this.editId = null;
      this.onRestaurantSelect();
    });
  }

  deleteFood(id: number) {
    this.foodService.deleteFood(id).subscribe(() => {
      this.message = 'Food deleted successfully!';
      this.onRestaurantSelect();
    });
  }
}
