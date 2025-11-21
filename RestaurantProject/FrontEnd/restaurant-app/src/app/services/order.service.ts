import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Order } from '../models/order.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class OrderService {

  // Correct base URL through Angular proxy → API Gateway
  private baseUrl = '/order';

  constructor(private http: HttpClient) {}

  placeOrder(order: Order): Observable<any> {
    return this.http.post(`${this.baseUrl}/saveOrder`, order);
  }

  getAllOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.baseUrl}/getAllOrders`);
  }

  getOrdersByUser(userId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.baseUrl}/getOrdersByUser/${userId}`);
  }

  getOrdersByRestaurant(restaurantId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.baseUrl}/getOrdersByRestaurant/${restaurantId}`);
  }

  markAsProcessed(orderId: number): Observable<Order> {
    return this.http.put<Order>(`${this.baseUrl}/markProcessed/${orderId}`, {});
  }
}
