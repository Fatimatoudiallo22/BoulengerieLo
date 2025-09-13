import { Component ,OnInit} from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-navbar',
  imports: [FormsModule,CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  isAdmin:boolean = false;
   isEmploye:boolean = false;
  isClient:boolean = false;
   userRole: string = ''; // ✅ déclarer la variable pour le rôle

  constructor (private authService :AuthService ,private router: Router  ) { }
  ngOnInit(): void {
  const role = localStorage.getItem('user_role');
  this.userRole = role || '';        // 'admin', 'employe', 'client' ou ''
  this.isAdmin = this.userRole === 'admin';
  this.isEmploye = this.userRole === 'employe';
  this.isClient = this.userRole === 'client';
}
 
  gotoProduits(){
    this.router.navigate(['/produits']);
    
  }
  gotoCategories(){
    this.router.navigate(['/categories']);
  }
  gotoFactures(){
    this.router.navigate(['/factures']);
  }
  gotoLivraisons(){
    this.router.navigate(['/livraisons']);
  }
  gotoCommandes(){
    this.router.navigate(['/commandes']);
  }
  
  gotoDashboard(){
    this.router.navigate(['/acceuil']);
  }
  gotoUtilisateurs(){
    this.router.navigate(['/utilisateurs']);
  }
  gotoMessagerie(){
    this.router.navigate(['/messagerie']);
  }
  gotoPromotions(){
    this.router.navigate(['/promotions']);
  }
  gotoPromoListe(){
    this.router.navigate(['/promo-liste']);
  }
  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
