import { Component , OnInit} from '@angular/core';
import { PackService, Pack } from '../../../services/pack/pack.service';
@Component({
  selector: 'app-pack',
  imports: [],
  templateUrl: './pack.component.html',
  styleUrl: './pack.component.css'
})
export class PackComponent implements OnInit {
  packs: Pack[] = [];
  selectedPack?: Pack;

  constructor(private packService: PackService) {}

  ngOnInit(): void {
    this.loadPacks();
  }

  loadPacks() {
    this.packService.getPacks().subscribe(data => this.packs = data);
  }

  selectPack(pack: Pack) {
    this.selectedPack = pack;
  }

  deletePack(id: number) {
    this.packService.deletePack(id).subscribe(() => this.loadPacks());
  }
}
