import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { Order } from '../../models/order.model'; // adjust path

interface MyJwtPayload {
  sub: string; // userId
  name?: string;
}

@Component({
  selector: 'app-order-success',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-success.component.html',
  styleUrls: ['./order-success.component.css']
})
export class OrderSuccessComponent {
  order?: Order;
  subtotal = 0;
  tip = 0;
  tax = 0;
  total = 0;
  currentDateTime: Date = new Date(); // ✅ current date & time
  userId?: number;

  constructor(private router: Router) {
    // Decode JWT
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const decoded = jwtDecode<MyJwtPayload>(token);
        this.userId = Number(decoded.sub);
      } catch (err) {
        console.error('Failed to decode JWT', err);
      }
    }

    // Retrieve order from router state
    const nav = this.router.getCurrentNavigation();
    this.order = nav?.extras?.state?.['order'];

    if (this.order) {
      // Calculate subtotal
      this.subtotal = this.order.foodItems.reduce(
        (sum: number, f: { price: number; quantity: number }) => sum + f.price * f.quantity,
        0
      );

      // Tip (10%) and Tax (5%) calculation
      this.tip = this.subtotal * 0.1;
      this.tax = this.subtotal * 0.05;

      // Total including tip and tax
      this.total = this.subtotal + this.tip + this.tax;
    }
  }

  goHome(): void {
    this.router.navigate(['/']);
  }
}
