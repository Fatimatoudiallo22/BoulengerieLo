import { Component , OnInit } from '@angular/core';
import { CategorieService ,Categorie } from '../../../services/categorie/categorie.service';
import { FormBuilder, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-categorie',
  imports: [FormsModule,CommonModule],
  templateUrl: './categorie.component.html',
  styleUrl: './categorie.component.css'
})
export class CategorieComponent implements OnInit {

  categories: Categorie[] = [];
  newCategorie: Categorie = { nom: '', description: '' };
  editCategorieId: number | null = null;
  editCategorieName: string = '';
  editCategorieDescription: string = '';

  constructor(private categorieService: CategorieService) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categorieService.getCategories().subscribe(data => this.categories = data);
  }

  addCategorie(): void {
    if (!this.newCategorie.nom.trim()) return;
    this.categorieService.addCategorie(this.newCategorie).subscribe(cat => {
      this.categories.push(cat);
      this.newCategorie = { nom: '', description: '' };
    });
  }

  startEdit(categorie: Categorie): void {
    this.editCategorieId = categorie.id!;
    this.editCategorieName = categorie.nom;
    this.editCategorieDescription = categorie.description ?? '';
  }

  updateCategorie(): void {
    if (!this.editCategorieId) return;
    const updated: Categorie = {
      id: this.editCategorieId,
      nom: this.editCategorieName,
      description: this.editCategorieDescription
    };
    this.categorieService.updateCategorie(updated).subscribe(() => {
      const index = this.categories.findIndex(c => c.id === this.editCategorieId);
      if (index !== -1) this.categories[index] = updated;
      this.editCategorieId = null;
    });
  }

  deleteCategorie(id: number): void {
    if (!confirm('Supprimer cette catégorie ?')) return;
    this.categorieService.deleteCategorie(id).subscribe(() => {
      this.categories = this.categories.filter(c => c.id !== id);
    });
  }
}
