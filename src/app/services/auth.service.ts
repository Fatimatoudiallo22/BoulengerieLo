import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://127.0.0.1:8000/api/';

  constructor(private http: HttpClient) {}

  // Connexion
  login(credentials: { email: string; password: string }) {
    return this.http.post<any>(
      `${this.apiUrl}login`,
      credentials,
      {
        headers: { 'Content-Type': 'application/json' }
      }
    ).pipe(
      tap({
        next: (res) => {
          console.log('Login API response:', res);
          if (res.token && res.user) {
            // Stocker le token
            localStorage.setItem('auth_token', res.token);
            // Stocker éventuellement le rôle (si renvoyé par Laravel)
            localStorage.setItem('user_role', res.user.role ?? '');
              // Stocker l'ID utilisateur
            localStorage.setItem('user_id', res.user.id); 
          } else {
            alert('Token ou utilisateur manquant dans la réponse');
          }
        },
        error: (err) => {
          console.error('Erreur login API :', err);
          alert('Échec de la connexion.');
        }
      })
    );
  }

  // Déconnexion
  logout() {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('user_role');
  }

  // Vérifie si connecté
  isLoggedIn(): boolean {
    return !!localStorage.getItem('auth_token');
  }

  // Récupère le rôle stocké
  getRole(): string | null {
    return localStorage.getItem('user_role');
  }

  // Récupère le token stocké
  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  // Exemple : appel API protégée
  getUser() {
    const token = this.getToken();
    return this.http.get<any>(`${this.apiUrl}user`, {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
    });
  }
}
