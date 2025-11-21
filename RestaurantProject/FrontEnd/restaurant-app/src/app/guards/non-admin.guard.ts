import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const NonAdminGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (!auth.isLoggedIn()) {
    const path = window.location.pathname.slice(1);
    router.navigate(['/login'], { queryParams: { redirect: path } });
    return false;
  }

  if (auth.isAdmin()) {
    router.navigate(['/admin']);
    return false;
  }

  return true;
};
