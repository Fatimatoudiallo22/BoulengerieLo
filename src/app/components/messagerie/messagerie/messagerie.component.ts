import { Component,OnInit } from '@angular/core';
import { MessagerieService, Message} from '../../../services/messagerie/messagerie.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ViewChild, ElementRef } from '@angular/core';
import { AfterViewChecked ,} from '@angular/core';
import { Observable } from 'rxjs';
import { UtilisateurService,Utilisateur } from '../../../services/utilisateur/utilisateur.service';
export interface SendMessageResponse {
  message: string;
  data: Message;
}

@Component({
  selector: 'app-messagerie',
  imports: [CommonModule,FormsModule],
  templateUrl: './messagerie.component.html',
  styleUrl: './messagerie.component.css'
})


export class MessagerieComponent implements OnInit {

  messages: Message[] = [];
  newMessage: string = '';
  destinataireId!: number;
  userId: number = Number(localStorage.getItem('user_id'));
  employes: Utilisateur[] = []; // ✅ tableau pour stocker les employés

  constructor(
    private messagerieService: MessagerieService,
    private utilisateurService: UtilisateurService // ✅ injecter le service
  ) {}

  ngOnInit(): void {
    this.loadEmployes();
  }

  loadConversation() {
    if (!this.destinataireId) return;
    this.messagerieService.getConversationWith(this.destinataireId).subscribe({
      next: (data) => this.messages = data,
      error: (err) => console.error('Erreur chargement messages', err)
    });
  }

  loadEmployes() {
    this.utilisateurService.getUtilisateurs().subscribe({
      next: (data) => this.employes = data,
      error: (err) => console.error('Erreur chargement employés', err)
    });
  }

  selectDestinataire(id: number) {
    this.destinataireId = id;
    this.loadConversation();
  }

sendMessage() {
  if (!this.newMessage || !this.destinataireId) return;

  const payload = {
    destinataire_id: this.destinataireId,
    message: this.newMessage,
    expediteur_id: this.userId
  };

  console.log('Envoi d\'un message avec payload :', payload);

  this.messagerieService.sendMessage(this.destinataireId, this.newMessage).subscribe({
    next: (res) => {
      this.messages.push(res.data); // Ajouter le message envoyé
      this.newMessage = '';          // Réinitialiser le champ
    },
    error: (err) => console.error('Erreur envoi message', err)
  });
}
/*

  envoyerMessage() {
    if (!this.newMessage.trim() || !this.destinataireId) return;
    this.messagerieService.sendMessage(this.destinataireId, this.newMessage).subscribe({
      next: (response) => {
        console.log('Message envoyé avec succès :', response);
        this.newMessage = '';
        this.loadConversation();
      },
      error: (err) => console.error('Erreur envoi message', err)
    });
  }
*/
  getNomExpediteur(id: number) {
    const emp = this.employes.find(e => e.id === id);
    return emp ? emp.nom : 'Autre';
  }

  markAsRead(message: Message) {
    if (message.lu_le) return;
    this.messagerieService.markAsRead(message.id!).subscribe({
      next: () => message.lu_le = new Date().toISOString(),
      error: (err) => console.error('Erreur marquage lu', err)
    });
  }
}


