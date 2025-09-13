import { Component , OnInit } from '@angular/core';
import { LivraisonService, Livraison } from '../../../services/livraison/livraison.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CommandeDetailComponent } from '../../commandeDetail/commande-detail/commande-detail.component';

@Component({
  selector: 'app-livraison',
  imports: [FormsModule,CommonModule],
  templateUrl: './livraison.component.html',
  styleUrl: './livraison.component.css'
})
export class LivraisonComponent implements OnInit {
  livraisons: Livraison[] = [];
  selectedLivraison?: Livraison;
livraisonForm = { 
    adresse: '',
    date_prevue: '',
   statut: 'en_preparation'
  
    };
  constructor(private livraisonService: LivraisonService) {}

  ngOnInit(): void {
    this.loadLivraisons();
  }

 loadLivraisons() {
    this.livraisonService.getLivraisons().subscribe({
      next: data => {
        console.log('Livraisons reçues:', data); // debug
        this.livraisons = data;
      },
      error: err => console.error('Erreur récupération livraisons', err)
    });
  }

  selectLivraison(livraison: Livraison) {
    this.selectedLivraison = livraison;
  }

  deleteLivraison(id: number) {
    this.livraisonService.deleteLivraison(id).subscribe(() => this.loadLivraisons());
  }
}
