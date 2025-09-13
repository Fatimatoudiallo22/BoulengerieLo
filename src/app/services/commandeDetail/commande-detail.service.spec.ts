import { TestBed } from '@angular/core/testing';

import { CommandeDetailService } from './commande-detail.service';

describe('CommandeDetailService', () => {
  let service: CommandeDetailService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommandeDetailService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
