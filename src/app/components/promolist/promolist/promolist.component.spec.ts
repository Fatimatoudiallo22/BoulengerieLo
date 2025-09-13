import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PromolistComponent } from './promolist.component';

describe('PromolistComponent', () => {
  let component: PromolistComponent;
  let fixture: ComponentFixture<PromolistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PromolistComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PromolistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
