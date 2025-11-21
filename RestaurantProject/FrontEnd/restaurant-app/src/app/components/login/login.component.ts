import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;
  loading: boolean = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  submit(): void {
    if (this.form.invalid) return;

    this.loading = true;
    this.error = null;

    const credentials = {
      username: this.form.value.username!,
      password: this.form.value.password!
    };

    this.auth.login(credentials).subscribe({
      next: (res) => {
        this.loading = false;
        this.auth.storeToken(res.token, res.role);

        // Get the original requested URL (if any)
        const redirect = this.route.snapshot.queryParams['redirect'];

        if (redirect) {
          // Navigate to originally requested page
          this.router.navigate(['/' + redirect]);
        } else if (this.auth.isAdmin()) {
          // Admin default page
          this.router.navigate(['/admin']);
        } else {
          // Normal user default page
          this.router.navigate(['/restaurants']);
        }
      },
      error: (err) => {
        this.loading = false;
        this.error = err?.error?.message || 'Invalid login credentials';
      }
    });
  }
}
