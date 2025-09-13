import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Message {
  id?: number;
  expediteur_id: number;
  destinataire_id: number;
  message: string;
  lu_le?: string | null;
  created_at?: string;
  updated_at?: string;
  expediteur?: any;
  destinataire?: any;
}
 export interface SendMessageResponse {
  message: string;
  data: Message;
}

@Injectable({
  providedIn: 'root'
})
export class MessagerieService {
  private apiUrl = 'http://localhost:8000/api/messageries'; // 🔹 adapte selon ton backend

  constructor(private http: HttpClient) {}

  // Génère les headers avec le token stocké
  private getHttpHeaders(): { headers: HttpHeaders } {
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('auth_token')}`,
        'Content-Type': 'application/json'
      })
    };
  }

  // Récupérer conversation entre l’utilisateur connecté et un autre
// MessagerieService
getConversationWith(userId: number): Observable<Message[]> {
  return this.http.get<Message[]>(
    `${this.apiUrl}/conversation/${userId}`,
    this.getHttpHeaders()
  );
}

  // Envoyer un message
 sendMessage(destinataireId: number, message: string): Observable<SendMessageResponse> {
  return this.http.post<SendMessageResponse>(
    `${this.apiUrl}/`,
    { destinataire_id: destinataireId, message },
    this.getHttpHeaders()
  );
}


  // Marquer un message comme lu
  markAsRead(messageId: number): Observable<any> {
    return this.http.patch(`${this.apiUrl}/${messageId}/`, {}, this.getHttpHeaders());
  }
}
