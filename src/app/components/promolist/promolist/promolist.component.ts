import { Component ,OnInit } from '@angular/core';
import { Promotion } from '../../../services/promotion/promotion.service';
import { Produit, ProduitService } from '../../../services/produit/produit.service';
import { PromotionService } from '../../../services/promotion/promotion.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-promolist',
  imports: [FormsModule,CommonModule],
  templateUrl: './promolist.component.html',
  styleUrl: './promolist.component.css'
})
export class PromolistComponent implements OnInit {
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