import { Component , OnInit} from '@angular/core';
import { FactureService, Facture } from '../../../services/facture/facture.service';
@Component({
  selector: 'app-facture',
  imports: [],
  templateUrl: './facture.component.html',
  styleUrl: './facture.component.css'
})
export class FactureComponent implements OnInit {
  factures: Facture[] = [];
  selectedFacture?: Facture;

  constructor(private factureService: FactureService) {}

  ngOnInit(): void {
    this.loadFactures();
  }
  

  loadFactures() {
    this.factureService.getFactures().subscribe(data => {
      this.factures = data;
    });
  }

  selectFacture(facture: Facture) {
    this.selectedFacture = facture;
  }

  deleteFacture(id: number) {
    this.factureService.deleteFacture(id).subscribe(() => this.loadFactures());
  }
}
