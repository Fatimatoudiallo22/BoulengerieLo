// src/app/services/pack/pack.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Pack {
  id?: number;
  nom: string;
  description?: string;
  prix: number;
  produits?: { id: number; quantite: number }[];
}

@Injectable({
  providedIn: 'root'
})
export class PackService {
  private apiUrl = 'http://localhost:5072/api/packs';

  constructor(private http: HttpClient) {}

  getHttpHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('auth_token')}`,
      'Content-Type': 'application/json'
    });
  }

  getPacks(): Observable<Pack[]> {
    return this.http.get<Pack[]>(this.apiUrl, { headers: this.getHttpHeaders() });
  }

  getPackById(id: number): Observable<Pack> {
    return this.http.get<Pack>(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }

  addPack(pack: Pack): Observable<Pack> {
    return this.http.post<Pack>(this.apiUrl, pack, { headers: this.getHttpHeaders() });
  }

  updatePack(pack: Pack): Observable<Pack> {
    return this.http.put<Pack>(`${this.apiUrl}/${pack.id}`, pack, { headers: this.getHttpHeaders() });
  }

  deletePack(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }
}
