import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Categorie } from '../categorie/categorie.service';

export interface Produit {
  id?: number;
  nom: string;
  description?: string;
  prix: number;
  stock: number;
  image?: string;
  allergenes?: string;
  statut?: 'disponible' | 'indisponible';
  categorie_id?: number;
  categorie?: Categorie;
   promotion?: {
    id: number;
    titre: string;
    type: 'pourcentage' | 'montant';
    valeur: number;
    date_debut: string;
    date_fin: string;
  };
}

@Injectable({
  providedIn: 'root'
})
export class ProduitService {
  private apiUrl = 'http://localhost:8000/api/produits';

  constructor(private http: HttpClient) {}

  private getHttpHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('auth_token')}`
      // ⚠️ Ne pas mettre 'Content-Type' pour FormData, le navigateur le gère
    });
  }

  getProduits(): Observable<Produit[]> {
    return this.http.get<Produit[]>(this.apiUrl, { headers: this.getHttpHeaders() });
  }

  addProduit(formData: FormData): Observable<Produit> {
    return this.http.post<Produit>(this.apiUrl, formData, { headers: this.getHttpHeaders() });
  }

  updateProduit(id: number, formData: FormData): Observable<Produit> {
    return this.http.put<Produit>(`${this.apiUrl}/${id}`, formData, { headers: this.getHttpHeaders() });
  }

  deleteProduit(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }
}
