import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProduitService , Produit } from '../../../services/produit/produit.service';
import { Categorie, CategorieService } from '../../../services/categorie/categorie.service';
import { RouterLink } from '@angular/router';
import { NgxPaginationModule } from 'ngx-pagination';
@Component({
  selector: 'app-produit',
  imports: [FormsModule,CommonModule,RouterLink,NgxPaginationModule],
  templateUrl: './produit.component.html',
  styleUrl: './produit.component.css'
})
export class ProduitComponent implements OnInit {
  produits: Produit[] = [];
  categories: Categorie[] = [];

  newProduit: Produit = {
    nom: '',
    prix: 0,
    stock: 0,
    description: '',
    image: '',
    allergenes: '',
    statut: 'disponible',
    categorie_id: undefined
  };

  editProduitId: number | null = null;
  selectedFile?: File;

  constructor(
    private produitService: ProduitService,
    private categorieService: CategorieService
  ) {}

  ngOnInit(): void {
    this.loadProduits();
    this.loadCategories();
  }

  loadProduits(): void {
    this.produitService.getProduits().subscribe(data => this.produits = data);
    this.produitService.getProduits().subscribe(data => {
    this.produits = data.map(prod => {
      // Calcul du prix après promotion si existe
      if (prod.promotion) {
        const today = new Date();
        const debut = new Date(prod.promotion.date_debut);
        const fin = new Date(prod.promotion.date_fin);
        if (today >= debut && today <= fin) {
          if (prod.promotion.type === 'pourcentage') {
            prod.prix = prod.prix - (prod.prix * prod.promotion.valeur / 100);
          } else if (prod.promotion.type === 'montant') {
            prod.prix = prod.prix - prod.promotion.valeur;
          }
        }
      }
      return prod;
    });
  });
  }

  loadCategories(): void {
    this.categorieService.getCategories().subscribe(data => this.categories = data);
  }

  // 📌 Gestion du fichier sélectionné
  onFileSelected(event: any): void {
  if (event.target.files && event.target.files.length > 0) {
    const file: File = event.target.files[0];  // variable locale non-undefined
    this.selectedFile = file;

    const reader = new FileReader();
    reader.onload = () => this.newProduit.image = reader.result as string;
    reader.readAsDataURL(file);  // ✅ Utilisation de la variable locale
  }
}

  // 📌 Ajouter un produit
  addProduit(): void {
    if (!this.newProduit.nom.trim()) return;

    const formData = this.buildFormData(this.newProduit, this.selectedFile);

    this.produitService.addProduit(formData).subscribe({
      next: prod => {
        this.produits.push(prod);
        this.resetForm();
      },
      error: err => console.error('Erreur ajout produit:', err)
    });
  }

  // 📌 Mettre à jour un produit
  updateProduit(): void {
    if (!this.editProduitId) return;

    const formData = this.buildFormData(this.newProduit, this.selectedFile);

    this.produitService.updateProduit(this.editProduitId, formData).subscribe({
      next: updatedProd => {
        const index = this.produits.findIndex(p => p.id === this.editProduitId);
        if (index !== -1) this.produits[index] = updatedProd;
        this.editProduitId = null;
        this.resetForm();
      },
      error: err => console.error('Erreur mise à jour produit:', err)
    });
  }

  // 📌 Supprimer un produit
  deleteProduit(id: number): void {
    if (!confirm('Supprimer ce produit ?')) return;

    this.produitService.deleteProduit(id).subscribe({
      next: () => {
        this.produits = this.produits.filter(p => p.id !== id);
      },
      error: err => console.error('Erreur suppression produit:', err)
    });
  }

  // 📌 Commencer l’édition
  startEdit(produit: Produit): void {
    this.editProduitId = produit.id!;
    this.newProduit = { ...produit };
    this.selectedFile = undefined;
  }

  // 📌 Réinitialiser le formulaire
  resetForm(): void {
    this.newProduit = {
      nom: '',
      prix: 0,
      stock: 0,
      description: '',
      image: '',
      allergenes: '',
      statut: 'disponible',
      categorie_id: undefined
    };
    this.selectedFile = undefined;
  }

  // 📌 Générer FormData à partir d’un Produit et d’un fichier
private buildFormData(produit: Produit, file?: File): FormData {
  const fd = new FormData();
  fd.append('nom', produit.nom);
  fd.append('prix', produit.prix.toString());
  fd.append('stock', produit.stock.toString());
  fd.append('description', produit.description ?? '');
  fd.append('allergenes', produit.allergenes ?? '');
  fd.append('statut', produit.statut ?? 'disponible');

  if (produit.categorie_id != null) {
    fd.append('categorie_id', produit.categorie_id.toString());
  }

  // ✅ Ajouter le fichier uniquement s'il existe
  if (file !== undefined) {
    fd.append('image', file, file.name);
  }

  return fd;
}
page: number = 1; // page courante

//promotion pour chaque produit 

}