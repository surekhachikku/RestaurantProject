import { Routes } from '@angular/router';
import { RestaurantListComponent } from './components/restaurant-list/restaurant-list.component';
import { FoodListComponent } from './components/food-list/food-list.component';
import { OrderSuccessComponent } from './components/order-success/order-success.component';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { ContactComponent } from './components/contact/contact.component';
import { AdminComponent } from './components/admin/admin.component';
import { AddRestaurantComponent } from './components/admin/restaurant-details/add-restaurant/add-restaurant.component';
import { AddFoodComponent } from './components/admin/restaurant-details/add-food/add-food.component';
import { OrderListComponent } from './components/admin/order-list/order-list.component';
import { DeleteRestaurantComponent } from './components/admin/restaurant-details/delete-restaurant/delete-restaurant.component';
import { RestaurantDetailsComponent } from './components/admin/restaurant-details/restaurant-details.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { AdminGuard } from './guards/admin.guard';
import { NonAdminGuard } from './guards/non-admin.guard';

export const routes: Routes = [
  // Public
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'about', component: AboutComponent },
  { path: 'contact', component: ContactComponent },

  // User-only routes
  { path: 'restaurants', component: RestaurantListComponent, canActivate: [AuthGuard, NonAdminGuard] },
  { path: 'foods/:restaurantId', component: FoodListComponent, canActivate: [AuthGuard, NonAdminGuard] },
  { path: 'order-success', component: OrderSuccessComponent, canActivate: [AuthGuard, NonAdminGuard] },

  // Admin-only routes
  { path: 'admin', component: AdminComponent, canActivate: [AdminGuard] },
  { path: 'admin/restaurant-details', component: RestaurantDetailsComponent, canActivate: [AdminGuard] },
  { path: 'admin/restaurant-details/add-restaurant', component: AddRestaurantComponent, canActivate: [AdminGuard] },
  { path: 'admin/restaurant-details/delete-restaurant', component: DeleteRestaurantComponent, canActivate: [AdminGuard] },
  { path: 'admin/restaurant-details/add-food', component: AddFoodComponent, canActivate: [AdminGuard] },
  { path: 'admin/orders', component: OrderListComponent, canActivate: [AdminGuard] },

  // Fallback
  { path: '**', redirectTo: '' }
];
