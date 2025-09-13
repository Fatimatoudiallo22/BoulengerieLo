import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  imports: [FormsModule,CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email = '';
  password = '';

  constructor(private authService: AuthService,
          private router: Router 
  ) {}

  onLogin() {
    this.authService.login({ email: this.email, password: this.password }).subscribe({
      next: () => {
        alert('Connexion réussie ✅');
        const role = this.authService.getRole();
        if (role === 'admin') this.router.navigate(['/admin']);
        else if (role === 'client') this.router.navigate(['/client']);
        else if (role === 'employe') this.router.navigate(['/employe']);
        else alert('Rôle inconnu');
        
      }
      ,error: (err) => alert('Erreur de connexion ❌ : ' + err.message)
    });
  }
}
