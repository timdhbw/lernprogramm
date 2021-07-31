import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { AufgabentagEntityDetailComponent } from 'app/entities/aufgabentag-entity/aufgabentag-entity-detail.component';
import { AufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';

describe('Component Tests', () => {
  describe('AufgabentagEntity Management Detail Component', () => {
    let comp: AufgabentagEntityDetailComponent;
    let fixture: ComponentFixture<AufgabentagEntityDetailComponent>;
    const route = ({ data: of({ aufgabentagEntity: new AufgabentagEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabentagEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AufgabentagEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AufgabentagEntityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load aufgabentagEntity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aufgabentagEntity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
