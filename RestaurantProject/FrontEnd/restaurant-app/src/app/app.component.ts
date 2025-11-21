import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  //imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css', 
  imports: [
    HeaderComponent,
    FooterComponent,
    RouterOutlet      // ✅ include everything in ONE array
  ]
})
export class AppComponent {
  title = 'restaurant-app';
}
