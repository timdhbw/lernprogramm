import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { ProduktEntityDetailComponent } from 'app/entities/produkt-entity/produkt-entity-detail.component';
import { ProduktEntity } from 'app/shared/model/produkt-entity.model';

describe('Component Tests', () => {
  describe('ProduktEntity Management Detail Component', () => {
    let comp: ProduktEntityDetailComponent;
    let fixture: ComponentFixture<ProduktEntityDetailComponent>;
    const route = ({ data: of({ produktEntity: new ProduktEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [ProduktEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProduktEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProduktEntityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load produktEntity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.produktEntity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
