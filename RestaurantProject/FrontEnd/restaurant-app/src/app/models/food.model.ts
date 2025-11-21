export interface Food {
  id: number;
  itemName: string;
  isVeg: string;
  itemDescription?: string;
  price: number;
  quantity: number;
  restaurantId: number;
  selected?: boolean;
}