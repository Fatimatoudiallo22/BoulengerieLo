// src/app/services/facture.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Facture {
  id?: number;
  commande_id: number;
  numero?: string;
  montant: number;
  date_emission: string;
  pdf_path?: string;
}

@Injectable({
  providedIn: 'root'
})
export class FactureService {
  private apiUrl = 'http://localhost:8000/api/factures';

  constructor(private http: HttpClient) {}

  getHttpHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('auth_token')}`,
      'Content-Type': 'application/json'
    });
  }

  getFactures(): Observable<Facture[]> {
    return this.http.get<Facture[]>(this.apiUrl, { headers: this.getHttpHeaders() });
  }

  getFactureById(id: number): Observable<Facture> {
    return this.http.get<Facture>(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }

  addFacture(facture: Facture): Observable<Facture> {
    return this.http.post<Facture>(this.apiUrl, facture, { headers: this.getHttpHeaders() });
  }

  updateFacture(facture: Facture): Observable<Facture> {
    return this.http.put<Facture>(`${this.apiUrl}/${facture.id}`, facture, { headers: this.getHttpHeaders() });
  }

  deleteFacture(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }

downloadFacture(id: number): Observable<Blob> {
  return this.http.get(`${this.apiUrl}/${id}/download`, {
    headers: this.getHttpHeaders(),
    responseType: 'blob' // on veut récupérer un PDF
  });
}
}
