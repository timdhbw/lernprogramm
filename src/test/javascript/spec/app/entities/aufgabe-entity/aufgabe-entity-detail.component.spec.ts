import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { AufgabeEntityDetailComponent } from 'app/entities/aufgabe-entity/aufgabe-entity-detail.component';
import { AufgabeEntity } from 'app/shared/model/aufgabe-entity.model';

describe('Component Tests', () => {
  describe('AufgabeEntity Management Detail Component', () => {
    let comp: AufgabeEntityDetailComponent;
    let fixture: ComponentFixture<AufgabeEntityDetailComponent>;
    const route = ({ data: of({ aufgabeEntity: new AufgabeEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabeEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AufgabeEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AufgabeEntityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load aufgabeEntity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aufgabeEntity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
