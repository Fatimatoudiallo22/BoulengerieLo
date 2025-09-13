import { CommonModule  } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';

@Component({
  selector: 'app-acceuil',
  imports: [FormsModule,CommonModule],
  templateUrl: './acceuil.component.html',
  styleUrl: './acceuil.component.css'
})
export class AcceuilComponent  {
  constructor(private router: Router) {}
produits = [
    { nom: 'Baguette Tradition', prix: 1.20, image:'assets/images/neuf.png'},
    { nom: 'Croissant Beurre', prix: 0.90, image:'assets/images/deux.jpg'},
    { nom: 'Pain au Chocolat', prix: 1.10, image:'assets/images/trois.jpg'},
    { nom: 'Tarte aux Pommes', prix: 2.50, image:'assets/images/quatre.jpg'},
    { nom: 'Pain Complet', prix: 1.80, image:'assets/images/cinq.jpg'},
    { nom: 'Brioche Moelleuse', prix: 2.00, image:'assets/images/six.jpg'},
    { nom: 'Éclair au Chocolat', prix: 2.20, image:'assets/images/sept.jpg'},
    { nom: 'Madeleine Maison', prix: 1.50, image:'assets/images/huit.jpg'}
  ];
gotoCommandes(){
    this.router.navigate(['/commandes']);
  }
  gotoListpromotions(){
    this.router.navigate(['/promo-liste']);
  }
}

