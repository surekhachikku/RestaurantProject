import { Food } from './food.model';
import { User } from './user.model';

export interface Order {
  orderId?: number;
  foodItems: Food[];
  restaurant: any;  // 👈 we send the full restaurant object here
  //userId: number;
  user: User;
  totalPrice: number;  
  processed?: boolean;
  orderPlacedDate?: string; // ISO string returned from backend
}