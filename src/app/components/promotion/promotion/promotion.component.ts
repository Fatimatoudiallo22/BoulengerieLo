import { Component , OnInit } from '@angular/core';
import { PromotionService, Promotion } from '../../../services/promotion/promotion.service';
import { ProduitService, Produit } from '../../../services/produit/produit.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
@Component({
  selector: 'app-promotion',
  imports: [FormsModule,CommonModule,ReactiveFormsModule],
  templateUrl: './promotion.component.html',
  styleUrl: './promotion.component.css'
})
export class PromotionComponent implements OnInit {
  promotions: Promotion[] = [];
  produits: Produit[] = [];
  promotionForm!: FormGroup;
  editPromotionId?: number;

  constructor(
    private promotionService: PromotionService,
    private produitService: ProduitService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loadPromotions();
    this.loadProduits();
    this.initForm();
  }

  // Initialiser le formulaire
  initForm(): void {
    this.promotionForm = this.fb.group({
      titre: ['', Validators.required],
      description: [''],
      type: ['pourcentage', Validators.required],
      valeur: [0, [Validators.required,]],
      date_debut: [''],
      date_fin: [''],
      produit_id: [null]
    });
  }

  // Charger les promotions existantes
  loadPromotions(): void {
    this.promotionService.getPromotions().subscribe(data => this.promotions = data);
  }

  // Charger les produits pour le select
  loadProduits(): void {
    this.produitService.getProduits().subscribe(data => this.produits = data);
  }

  // Ajouter ou mettre à jour une promotion
  savePromotion(): void {
  console.log('Formulaire soumis', this.promotionForm.value);
  if (this.promotionForm.invalid) {
    console.log('Formulaire invalide');
    return;
  }

  const promoData: Promotion = this.promotionForm.value;
  console.log('Promo à envoyer au backend:', promoData);

  this.promotionService.addPromotion(promoData).subscribe({
    next: res => {
      console.log('Promotion ajoutée:', res);
      this.loadPromotions();
      this.resetForm();
    },
    error: err => console.error('Erreur ajout promotion:', err)
  });
}


  // Commencer l’édition d’une promotion
  startEdit(promo: Promotion): void {
    this.editPromotionId = promo.id;
    this.promotionForm.patchValue({
      titre: promo.titre,
      description: promo.description,
      type: promo.type,
      valeur: promo.valeur,
      date_debut: promo.date_debut,
      date_fin: promo.date_fin,
      produit_id: promo.produit_id
    });
  }

  // Supprimer une promotion
  deletePromotion(id: number): void {
    if (!confirm('Supprimer cette promotion ?')) return;
    this.promotionService.deletePromotion(id).subscribe(() => this.loadPromotions());
  }

  // Réinitialiser le formulaire
  resetForm(): void {
    this.editPromotionId = undefined;
    this.promotionForm.reset({
      titre: '',
      description: '',
      type: 'pourcentage',
      valeur: 0,
      date_debut: '',
      date_fin: '',
      produit_id: null
    });
  }

  getProduitName(id?: number): string {
  if (!id || !this.produits) return '';
  const prod = this.produits.find(p => p.id === id);
  return prod ? prod.nom : '';
}

}
