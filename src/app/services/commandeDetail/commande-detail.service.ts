import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CommandeDetail {
  id?: number;
  commande_id: number;
  produit_id: number;
  quantite: number;
  prix_unitaire: number;
}

@Injectable({
  providedIn: 'root'
})
export class CommandeDetailService {

  private apiUrl = 'http://localhost:8000/api/commande-details';

  constructor(private http: HttpClient) { }

  getHttpHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('auth_token')}`,
      'Content-Type': 'application/json'
    });
  }

  getCommandeDetails(): Observable<CommandeDetail[]> {
    return this.http.get<CommandeDetail[]>(this.apiUrl, { headers: this.getHttpHeaders() });
  }

  getCommandeDetailById(id: number): Observable<CommandeDetail> {
    return this.http.get<CommandeDetail>(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }

  addCommandeDetail(detail: CommandeDetail): Observable<CommandeDetail> {
    return this.http.post<CommandeDetail>(this.apiUrl, detail, { headers: this.getHttpHeaders() });
  }

  updateCommandeDetail(detail: CommandeDetail): Observable<CommandeDetail> {
    return this.http.put<CommandeDetail>(`${this.apiUrl}/${detail.id}`, detail, { headers: this.getHttpHeaders() });
  }

  deleteCommandeDetail(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHttpHeaders() });
  }
   getTotalCommandesByClient(clientId: number): Observable<{ client_id: number, total_commandes: number }> {
  return this.http.get<{ client_id: number, total_commandes: number }>(
    `${this.apiUrl}/total/${clientId}`, 
    { headers: this.getHttpHeaders() }
  );
}

}
