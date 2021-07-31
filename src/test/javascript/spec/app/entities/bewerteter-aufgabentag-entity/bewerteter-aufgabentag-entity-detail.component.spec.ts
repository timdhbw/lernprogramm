import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { BewerteterAufgabentagEntityDetailComponent } from 'app/entities/bewerteter-aufgabentag-entity/bewerteter-aufgabentag-entity-detail.component';
import { BewerteterAufgabentagEntity } from 'app/shared/model/bewerteter-aufgabentag-entity.model';

describe('Component Tests', () => {
  describe('BewerteterAufgabentagEntity Management Detail Component', () => {
    let comp: BewerteterAufgabentagEntityDetailComponent;
    let fixture: ComponentFixture<BewerteterAufgabentagEntityDetailComponent>;
    const route = ({ data: of({ bewerteterAufgabentagEntity: new BewerteterAufgabentagEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [BewerteterAufgabentagEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BewerteterAufgabentagEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BewerteterAufgabentagEntityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bewerteterAufgabentagEntity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bewerteterAufgabentagEntity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
