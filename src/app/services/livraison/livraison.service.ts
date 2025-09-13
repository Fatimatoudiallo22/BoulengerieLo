// src/app/services/livraison.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Livraison {
  id?: number;
  commande_id: number;
  employe_id?: number;
  adresse_livraison?: string;
  date_prevue?: string;
  date_livree?: string;
  statut: 'en_preparation' | 'en_livraison' | 'livree' | 'annulee';
}


@Injectable({
  providedIn: 'root'
})
export class LivraisonService {
  private apiUrl = 'http://localhost:8000/api/livraisons';

  constructor(private http: HttpClient) {}

  getHttpHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('auth_token')}`,
      'Content-Type': 'application/json'
    });
  }

  getLivraisons(): Observable<Livraison[]> {
    return this.http.get<Livraison[]>(this.apiUrl, { headers: this.getHttpHeaders() });
  }

  getLivraisonById(id: number): Observable<Livraison> {
    return this.http.get<Livraison>(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }

  addLivraison(livraison: Livraison): Observable<Livraison> {
    return this.http.post<Livraison>(this.apiUrl, livraison, { headers: this.getHttpHeaders() });
  }

  updateLivraison(livraison: Livraison): Observable<Livraison> {
    return this.http.put<Livraison>(`${this.apiUrl}/${livraison.id}`, livraison, { headers: this.getHttpHeaders() });
  }

  deleteLivraison(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }

  ajouterLivraison(livraison: Livraison) {
  return this.http.post<Livraison>(this.apiUrl, livraison);
}

}
