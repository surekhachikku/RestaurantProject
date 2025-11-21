import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const AuthGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (!auth.isLoggedIn()) {
    const path = window.location.pathname.slice(1); // remove leading '/'
    router.navigate(['/login'], { queryParams: { redirect: path } });
    return false;
  }

  return true;
};
