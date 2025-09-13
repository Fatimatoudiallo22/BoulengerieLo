import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-employe',
  imports: [],
  templateUrl: './employe.component.html',
  styleUrl: './employe.component.css'
})
export class EmployeComponent {
  isEmploye:boolean = false;
  constructor (private authService :AuthService ,private router: Router  ) { }
  gotoCommandes(){
    this.router.navigate(['/commandes']);
  }
  gotoFactures(){
    this.router.navigate(['/factures']);
  }
  gotoLivraisons(){
    this.router.navigate(['/livraisons']);
  }
 gotoProduits(){
    this.router.navigate(['/produits']);
  }
  gotoCategories(){
    this.router.navigate(['/categories']);
  }
  gotoDashboard(){
    this.router.navigate(['/dashboard']);
  }
  gotoMessagerie(){
    this.router.navigate(['/messagerie']);
  }
  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
  
}

