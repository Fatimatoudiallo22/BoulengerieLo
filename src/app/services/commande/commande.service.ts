import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
export interface CommandeDetail {
  produit_id: number;
  quantite: number;
  prix_unitaire: number;
  produit?: Produit;   // ajout
}
export interface Produit {
  id: number;
  nom: string;
  prix: number;
}

export interface Commande {
  id?: number;
  utilisateur_id?: number;   // correspond à client_id dans le backend
  prix_total: number;       // correspond à total dans le backend
  statut?: 'en_attente' | 'en_cours' | 'livree' | 'annulee';
  created_at?: string;
  updated_at?: string;
  details?: CommandeDetail[];
}
export interface CommandeCreate {
  prix_total: number;
  details: {
    produit_id: number;
    quantite: number;
    prix_unitaire: number;
  }[];
}

@Injectable({
  providedIn: 'root'
})
export class CommandeService {
  private apiUrl = 'http://localhost:8000/api/commandes'; // adapte si nécessaire

  constructor(private http: HttpClient) { }

  private getHttpHeaders(): HttpHeaders {
    // Vérifie si le token existe avant de l'envoyer
    const token = localStorage.getItem('auth_token');
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  // Récupérer toutes les commandes
  getCommandes(): Observable<Commande[]> {
    return this.http.get<Commande[]>(this.apiUrl, { headers: this.getHttpHeaders() });
  }

  // Récupérer une commande par ID
  getCommande(id: number): Observable<Commande> {
    // Correction : suppression de la virgule en trop
    return this.http.get<Commande>(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }

  // Créer une commande
 addCommande(commande: Commande): Observable<Commande> {
   const payload = {
    utilisateur_id: commande.utilisateur_id, // ✅ IMPORTANT
    prix_total: commande.prix_total,
    details: commande.details
  };
  return this.http.post<Commande>(this.apiUrl, payload, { headers: this.getHttpHeaders() });
}
  // Mettre à jour une commande
  updateCommande(id: number, commande: Partial<Commande>): Observable<Commande> {
    const payload = {
      total: commande.prix_total
    };
    return this.http.put<Commande>(`${this.apiUrl}/${id}`, payload, { headers: this.getHttpHeaders() });
  }

  // Supprimer une commande
  deleteCommande(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }

  // Payer une commande
  payerCommande(id: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${id}/payer`, {}, { headers: this.getHttpHeaders() });
  }
  // prix tottale de commande d'un client
 getTotalCommandesByClient(clientId: number): Observable<{ client_id: number, total_commandes: number }> {
  return this.http.get<{ client_id: number, total_commandes: number }>(
    `${this.apiUrl}/total/${clientId}`, 
    { headers: this.getHttpHeaders() }
  );
}
confirmerLivraison(commandeId: number, adresse: string, date_prevue: string): Observable<any> {
  const payload = {
    adresse_livraison: adresse,
    date_prevue: date_prevue
  };
  return this.http.post(`${this.apiUrl}/${commandeId}/confirmer`, payload, { headers: this.getHttpHeaders() });
}
}
