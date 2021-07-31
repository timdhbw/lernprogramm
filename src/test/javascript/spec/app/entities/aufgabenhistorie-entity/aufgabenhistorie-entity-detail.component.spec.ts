import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { AufgabenhistorieEntityDetailComponent } from 'app/entities/aufgabenhistorie-entity/aufgabenhistorie-entity-detail.component';
import { AufgabenhistorieEntity } from 'app/shared/model/aufgabenhistorie-entity.model';

describe('Component Tests', () => {
  describe('AufgabenhistorieEntity Management Detail Component', () => {
    let comp: AufgabenhistorieEntityDetailComponent;
    let fixture: ComponentFixture<AufgabenhistorieEntityDetailComponent>;
    const route = ({ data: of({ aufgabenhistorieEntity: new AufgabenhistorieEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabenhistorieEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AufgabenhistorieEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AufgabenhistorieEntityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load aufgabenhistorieEntity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aufgabenhistorieEntity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
