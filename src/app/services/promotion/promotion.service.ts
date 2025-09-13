// src/app/services/promotion/promotion.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Promotion {
  id?: number;
  titre: string;
  description?: string;
  type: 'pourcentage' | 'montant';
  valeur: number;
  date_debut?: string;
  date_fin?: string;
  produit_id?: number;
  pack_id?: number;
}

@Injectable({
  providedIn: 'root'
})
export class PromotionService {
  private apiUrl = 'http://127.0.0.1:8000/api/promotions';

  constructor(private http: HttpClient) {}

  getHttpHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('auth_token')}`,
      'Content-Type': 'application/json'
    });
  }

  getPromotions(): Observable<Promotion[]> {
    return this.http.get<Promotion[]>(this.apiUrl, { headers: this.getHttpHeaders() });
  }

  getPromotionById(id: number): Observable<Promotion> {
    return this.http.get<Promotion>(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }

  addPromotion(promo: Promotion): Observable<Promotion> {
    return this.http.post<Promotion>(this.apiUrl, promo, { headers: this.getHttpHeaders() });
  }

  updatePromotion(promo: Promotion): Observable<Promotion> {
    return this.http.put<Promotion>(`${this.apiUrl}/${promo.id}`, promo, { headers: this.getHttpHeaders() });
  }

  deletePromotion(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }
}
