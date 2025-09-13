import { Component , OnInit } from '@angular/core';
import { CommandeService , Commande } from '../../../services/commande/commande.service';
import { CommandeDetailService} from '../../../services/commandeDetail/commande-detail.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ProduitService ,Produit } from '../../../services/produit/produit.service';
import { RouterLink } from '@angular/router';
import { FactureService } from '../../../services/facture/facture.service'; // ✅ importé
import { CommandeCreate } from '../../../services/commande/commande.service'; // ✅ importé
import { AuthService } from '../../../services/auth.service'; // ✅ importé
import { NgxPaginationModule } from 'ngx-pagination';
@Component({
  selector: 'app-commande',
  imports: [FormsModule,CommonModule,RouterLink,NgxPaginationModule],
  templateUrl: './commande.component.html',
  styleUrl: './commande.component.css'
})
export class CommandeComponent implements OnInit {
  produits: Produit[] = [];
  commandes: Commande[] = [];
// roleId: string = localStorage.getItem('role') || 'client';

// roleId: number =Number(localStorage.getItem('role_id')||'client');
  showCommandes = false; // contrôle l'affichage de la liste des commandes

  constructor(
    private produitService: ProduitService,
    private commandeService: CommandeService,
    private factureService: FactureService // ✅ injecté
    , private authService: AuthService // ✅ injecté
  ) {}

  ngOnInit(): void {
    this.loadProduits();
     this.loadCommandes();
   
    //this.totalCommandesClient(this.userId);
  }

  loadProduits() {
    this.produitService.getProduits().subscribe(
      data => this.produits = data,
      err => console.error('Erreur chargement produits', err)
    );
  }

  loadCommandes() {
    this.commandeService.getCommandes().subscribe(
    data => {
      this.commandes = data; // ce que le backend envoie
    },
    err => console.error('Erreur chargement commandes', err)
  );
  }

commanderProduit(produit: Produit) {
  const userId = this.authService.getUser(); // récupère l'ID connecté

  if (!userId) {
    console.error('Utilisateur non connecté');
    return;
  }

  const nouvelleCommande = {
    utilisateur_id: Number(userId), // <-- ID dynamique
    prix_total: produit.prix,
    details: [
      {
        produit_id: Number(produit.id),
        quantite: 1,
        prix_unitaire: produit.prix
      }
    ]
  };

  this.commandeService.addCommande(nouvelleCommande).subscribe(
    res => {
      console.log('Commande créée', res);
      if (this.showCommandes) this.loadCommandes();
    },
    err => console.error('Erreur création commande', err)
  );
}




  toggleCommandes() {
    this.showCommandes = !this.showCommandes;
    if (this.showCommandes) this.loadCommandes(); // charger les commandes seulement si on veut les voir
  }

  payerCommande(commande: Commande) {
    if (!commande.id) return;
    this.commandeService.payerCommande(commande.id).subscribe(
      res => this.loadCommandes(),
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



   telechargerFacture(factureId: number) {
    this.factureService.downloadFacture(factureId).subscribe((pdfBlob) => {
      const blob = new Blob([pdfBlob], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `facture_${factureId}.pdf`;
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

  voirToutesCommandes() {
  this.commandeService.getCommandes().subscribe(
    data => {
      this.commandes = data;  // le backend renvoie toutes les commandes si admin/employé
      console.log('Toutes les commandes chargées ✅', this.commandes);
    },
    err => console.error('Erreur chargement toutes les commandes', err)
  );
}
page: number = 1;
}
