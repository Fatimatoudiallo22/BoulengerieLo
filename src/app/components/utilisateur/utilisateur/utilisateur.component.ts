import { Component , OnInit } from '@angular/core';
import { UtilisateurService, Utilisateur } from '../../../services/utilisateur/utilisateur.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-utilisateur',
  imports: [FormsModule,CommonModule],
  templateUrl: './utilisateur.component.html',
  styleUrl: './utilisateur.component.css'
})
export class UtilisateurComponent implements OnInit {
  utilisateurs: Utilisateur[] = [];
  selectedUtilisateur: Utilisateur = {
    id: 0,
    nom: '',
    prenom: '',
    email: '',
    password: '',
    role: 'client', // valeur par défaut obligatoire
    telephone: '',
    adresse: ''
  };

  constructor(private utilisateurService: UtilisateurService) {}

  ngOnInit(): void {
    this.loadUtilisateurs();
  }

  loadUtilisateurs(): void {
    this.utilisateurService.getUtilisateurs().subscribe(data => {
      this.utilisateurs = data;
    });
  }

  selectUtilisateur(utilisateur: Utilisateur): void {
    this.selectedUtilisateur = { ...utilisateur }; // copie pour éviter les mutations directes
  }

  clearSelection(): void {
    this.selectedUtilisateur = {
      id: 0,
      nom: '',
      prenom: '',
      email: '',
      password: '',
      role: 'client', // valeur par défaut
      telephone: '',
      adresse: ''
    };
  }

  saveUtilisateur(): void {
    if (this.selectedUtilisateur.id === 0) {
      // création
      this.utilisateurService.createUtilisateur(this.selectedUtilisateur).subscribe(() => {
        this.loadUtilisateurs();
        this.clearSelection();
      });
    } else {
      // mise à jour
      this.utilisateurService.updateUtilisateur(this.selectedUtilisateur.id, this.selectedUtilisateur)
        .subscribe(() => {
          this.loadUtilisateurs();
          this.clearSelection();
        });
    }
  }

  deleteUtilisateur(id: number): void {
    this.utilisateurService.deleteUtilisateur(id).subscribe(() => {
      this.loadUtilisateurs();
      if (this.selectedUtilisateur.id === id) this.clearSelection();
    });
  }
}