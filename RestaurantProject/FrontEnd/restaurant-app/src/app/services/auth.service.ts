import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Credentials {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  role: string;
  username: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = '/auth/login';
  private tokenKey = 'token';
  private roleKey = 'role';

  constructor(private http: HttpClient) {}

  login(credentials: Credentials): Observable<LoginResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });
    return this.http.post<LoginResponse>(this.apiUrl, credentials, { headers });
  }

  storeToken(token: string, role?: string) {
    localStorage.setItem(this.tokenKey, token);
    if (role) localStorage.setItem(this.roleKey, role);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getRole(): string | null {
    return localStorage.getItem(this.roleKey);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.roleKey);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  isAdmin(): boolean {
    return this.getRole() === 'ROLE_ADMIN';
  }
}
