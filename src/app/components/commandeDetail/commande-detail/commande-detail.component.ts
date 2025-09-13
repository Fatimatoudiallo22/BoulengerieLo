import { Component ,OnInit } from '@angular/core';
import { CommandeService, Commande } from '../../../services/commande/commande.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LivraisonService, Livraison } from '../../../services/livraison/livraison.service';
import { RouterLink } from '@angular/router';
import { dateTimestampProvider } from 'rxjs/internal/scheduler/dateTimestampProvider';

@Component({
  selector: 'app-commande-detail',
  imports: [FormsModule,CommonModule],
  templateUrl: './commande-detail.component.html',
  styleUrl: './commande-detail.component.css'
})
export class CommandeDetailComponent implements OnInit {
  
commandes: Commande[] = [];
userId: number = Number(localStorage.getItem('user_id') || '0');

  roleId: string = localStorage.getItem('role_id') || 'client';
  commandeSelectionnee?: Commande;
    livraisonForm = { 
    adresse: '',
    date_prevue: '',
   statut: 'en_preparation'
      
    };
    
  constructor(private commandeService: CommandeService,
        private livraisonService: LivraisonService
       
  ) {}

  ngOnInit(): void {
    this.loadCommandes();
   
    this.totalCommandesClient(this.userId);

  }

loadCommandes() {
  this.commandeService.getCommandes().subscribe(
    data => {
      this.commandes = data; // ce que le backend envoie
    },
    err => console.error('Erreur chargement commandes', err)
  );
}


  payerCommande(commande: Commande) {
    if (!commande.id) return;
    this.commandeService.payerCommande(commande.id).subscribe(
      () => this.loadCommandes(),
      err => console.error('Erreur paiement', err)
    );
  }

  supprimerCommande(commande: Commande) {
    if (!commande.id) return;
    this.commandeService.deleteCommande(commande.id).subscribe(
      () => this.loadCommandes(),
      err => console.error('Erreur suppression', err)
    );
  }
 // --- NOUVEAU ---
  validerCommande(commande: Commande) {
    this.commandeSelectionnee = commande;
    this.livraisonForm = { adresse: '', date_prevue: '' ,statut: 'en_preparation'};
  }

// CommandeDetailComponent.ts
confirmerLivraison() {
  if (!this.commandeSelectionnee) return;

  const nouvelleLivraison: Livraison = {
    commande_id: this.commandeSelectionnee.id!,
    adresse_livraison: this.livraisonForm.adresse,
    date_prevue: new Date(this.livraisonForm.date_prevue).toISOString(),
    
    statut: 'en_preparation'
  };

  this.livraisonService.addLivraison(nouvelleLivraison).subscribe({
    next: () => {
      console.log("Livraison enregistrée ✅");
      this.livraisonForm = { adresse: '', date_prevue: '' ,statut: 'en_preparation'};
      this.commandeSelectionnee = undefined;
      this.loadCommandes();
    },
    error: (err) => console.error("Erreur lors de l'ajout de livraison", err)
  });
}



  annulerLivraison() {
    this.commandeSelectionnee = undefined;
  }
//le pix tottale de commande d'un client
totalClient: number = 0;

totalCommandesClient(clientId: number) {
  this.commandeService.getTotalCommandesByClient(clientId).subscribe({
    next: (res) => {
      this.totalClient = res.total_commandes;
      console.log(`Total des commandes pour le client ${clientId} : ${this.totalClient} €`);
    },
    error: (err) => console.error('Erreur récupération total commandes', err)
  });
}




confirmerLivraisons() {
  if (!this.commandeSelectionnee) return;

  this.commandeService.confirmerLivraison(
    this.commandeSelectionnee.id!,
    this.livraisonForm.adresse,
    this.livraisonForm.date_prevue
  ).subscribe({
    next: (res: any) => {
      console.log("✅ Livraison confirmée et mail envoyé", res);
      this.commandeSelectionnee = undefined;
      this.livraisonForm = { adresse: '', date_prevue: '', statut: 'en_preparation' };
      this.loadCommandes(); // recharge les commandes
    },
    error: (err: any) => console.error('Erreur confirmation livraison', err)
  });
}


}