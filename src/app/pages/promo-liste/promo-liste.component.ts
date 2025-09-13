import { Component,OnInit } from '@angular/core';
import { PromotionService} from '../../services/promotion/promotion.service';
import { ProduitService} from '../../services/produit/produit.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {Produit} from '../../services/produit/produit.service';
import {Promotion} from '../../services/promotion/promotion.service';
@Component({
  selector: 'app-promo-liste',
  imports: [CommonModule,FormsModule],
  templateUrl: './promo-liste.component.html',
  styleUrl: './promo-liste.component.css'
})
export class PromoListeComponent implements OnInit {
promotions: Promotion[] = [];
  produits: Produit[] = [];

  constructor(
    private promotionService: PromotionService,
    private produitService: ProduitService
  ) {}

  ngOnInit(): void {
    this.loadPromotions();
    this.loadProduits();
  }

  loadPromotions(): void {
    this.promotionService.getPromotions().subscribe(data => this.promotions = data);
  }

  loadProduits(): void {
    this.produitService.getProduits().subscribe(data => this.produits = data);
  }

  getProduitName(id?: number): string {
    if (!id) return '';
    const prod = this.produits.find(p => p.id === id);
    return prod ? prod.nom : '';
  }

  deletePromotion(id: number): void {
    if (!confirm('Supprimer cette promotion ?')) return;
    this.promotionService.deletePromotion(id).subscribe(() => this.loadPromotions());
  }
}