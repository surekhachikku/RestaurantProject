import { Food } from './food.model';

export interface FoodResponse {
  foodItemList: Food[];
restaurant: any;

 /* restaurant: {
    id: number;
    name: string;
    address: string;
    city: string;
    restaurantDescription?: string;
  };*/
}