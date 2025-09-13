import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { CategorieComponent } from './components/categorie/categorie/categorie.component';
import { ProduitComponent } from './components/produit/produit/produit.component';  
import { CommandeComponent } from './components/commande/commande/commande.component';
import { FactureComponent } from './components/facture/facture/facture.component';
import { LivraisonComponent } from './components/livraison/livraison/livraison.component';
import { PackComponent } from './components/pack/pack/pack.component';
import { PromotionComponent } from './components/promotion/promotion/promotion.component';
import { UtilisateurComponent } from './components/utilisateur/utilisateur/utilisateur.component';   
import { CommandeDetailComponent } from './components/commandeDetail/commande-detail/commande-detail.component';   
import { ProduitFormComponent } from './components/produit/produit-form/produit-form.component';
import { AdminComponent } from './components/dashboard/admin/admin.component';
import { ClientComponent } from './components/dashboard/client/client.component';
import { NavbarComponent } from './components/navbar/navbar/navbar.component';
import { AcceuilComponent } from './components/acceuil/acceuil/acceuil.component';
import { MessagerieComponent } from './components/messagerie/messagerie/messagerie.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
///import { PromoListeComponent } from './components/promo-liste/promo-liste.component';
import{PromolistComponent} from './components/promolist/promolist/promolist.component';
export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' }, // login par défaut
    {path: 'login', component: LoginComponent},
    {path: 'categories', component: CategorieComponent},    
    {path: 'produits', component: ProduitComponent},
    {path: 'commandes', component: CommandeComponent},  
    {path: 'factures', component: FactureComponent},    
    {path: 'livraisons', component: LivraisonComponent},    
    {path: 'packs', component: PackComponent},  
    {path: 'promotions', component: PromotionComponent},    
    {path: 'utilisateurs', component: UtilisateurComponent},  
    {path: 'mes-commandes', component: CommandeDetailComponent},  
    {path: 'ajouter-produit', component: ProduitFormComponent},
     {path: 'acceuil', component: AcceuilComponent},
    {path: 'messagerie', component: MessagerieComponent},
    //{path: 'promo-liste', component:PromoListeComponent},
    {path:'promo-liste',component:PromolistComponent},
   


    {path: 'admin', component: NavbarComponent,
        children: [
                    {path: 'categories', component: CategorieComponent},    
                    {path: 'produits', component: ProduitComponent},
                    {path: 'commandes', component: CommandeComponent},  
                    {path: 'factures', component: FactureComponent},    
                    {path: 'livraisons', component: LivraisonComponent},    
                    {path: 'packs', component: PackComponent},  
                    {path: 'promotions', component: PromotionComponent},    
                    {path: 'utilisateurs', component: UtilisateurComponent},  
                    {path: 'mes-commandes', component: CommandeDetailComponent},  
                    {path: 'ajouter-produit', component: ProduitFormComponent},
                    {path: 'acceuil', component: AcceuilComponent},
                    {path: 'messagerie', component: MessagerieComponent},

        ]
    },



      {path: 'client', component: ClientComponent,
        children: [
                    {path: 'categories', component: CategorieComponent},    
                    {path: 'produits', component: ProduitComponent},
                    {path: 'commandes', component: CommandeComponent},  
                    {path: 'factures', component: FactureComponent},    
                    {path: 'livraisons', component: LivraisonComponent},    
                    {path: 'packs', component: PackComponent},  
                    {path: 'promotions', component: PromotionComponent},    
                    {path: 'mes-commandes', component: CommandeDetailComponent},  
                     {path: 'dashboard', component: AcceuilComponent},
                     {path: 'messagerie', component: MessagerieComponent},
                  
        ]
    },

 {path: 'employe', component: ClientComponent,
        children: [
                    {path: 'categories', component: CategorieComponent},    
                    {path: 'produits', component: ProduitComponent},
                    {path: 'commandes', component: CommandeComponent},  
                    {path: 'factures', component: FactureComponent},    
                    {path: 'livraisons', component: LivraisonComponent},    
                    {path: 'packs', component: PackComponent},  
                    {path: 'promotions', component: PromotionComponent},    
                    {path: 'mes-commandes', component: CommandeDetailComponent},  
                     {path: 'dashboard', component: AcceuilComponent},
                  {path: 'messagerie', component: MessagerieComponent},
        ]
    }

];
