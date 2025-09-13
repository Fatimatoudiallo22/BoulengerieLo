import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PromoListeComponent } from './promo-liste.component';

describe('PromoListeComponent', () => {
  let component: PromoListeComponent;
  let fixture: ComponentFixture<PromoListeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PromoListeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PromoListeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
