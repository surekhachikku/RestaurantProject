import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OrderService } from '../../../services/order.service';
import { RestaurantService } from '../../../services/restaurant.service';
import { Order } from '../../../models/order.model';
import { Restaurant } from '../../../models/restaurant.model';
import { User } from '../../../models/user.model';

@Component({
  selector: 'app-view-orders',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {
  selectedRestaurantId: number | null = null;

  orders: Order[] = [];
  processedOrders: Order[] = [];
  restaurants: Restaurant[] = [];
  message = '';
  showArchivedOrders = false;

  constructor(
    private orderService: OrderService,
    private restaurantService: RestaurantService
  ) {}

  ngOnInit(): void {
    this.loadOrders();
    this.loadRestaurants();
  }

  loadRestaurants(): void {
    this.restaurantService.getRestaurants().subscribe({
      next: (res: Restaurant[]) => (this.restaurants = res),
      error: (err: any) => console.error('Error fetching restaurants:', err)
    });
  }

  loadOrders(): void {
    this.orderService.getAllOrders().subscribe({
      next: (res: Order[]) => {
        this.orders = res.filter(o => !o.processed);
        this.processedOrders = res.filter(o => o.processed);
      },
      error: (err: any) => console.error('Error fetching orders:', err)
    });
  }

  getOrdersByRestaurant(): void {
    if (!this.selectedRestaurantId) {
      this.loadOrders();
      return;
    }
    this.orderService.getOrdersByRestaurant(this.selectedRestaurantId).subscribe({
      next: (res: Order[]) => {
        this.orders = res.filter(o => !o.processed);
        this.processedOrders = res.filter(o => o.processed);
      },
      error: (err: any) => console.error('Error fetching orders by restaurant:', err)
    });
  }

  toggleArchived(): void {
    this.showArchivedOrders = !this.showArchivedOrders;
  }

  markProcessed(order: Order): void {
    this.orderService.markAsProcessed(order.orderId!).subscribe({
      next: () => {
        this.message = `✅ Order #${order.orderId} marked as processed`;

        // Remove from pending
        this.orders = this.orders.filter(o => o.orderId !== order.orderId);

        // Add to archived
        order.processed = true;
        this.processedOrders.push(order);
      },
      error: (err: any) => console.error('Error marking order processed:', err)
    });
  }
}
