import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { AufgabenteilEntityDetailComponent } from 'app/entities/aufgabenteil-entity/aufgabenteil-entity-detail.component';
import { AufgabenteilEntity } from 'app/shared/model/aufgabenteil-entity.model';

describe('Component Tests', () => {
  describe('AufgabenteilEntity Management Detail Component', () => {
    let comp: AufgabenteilEntityDetailComponent;
    let fixture: ComponentFixture<AufgabenteilEntityDetailComponent>;
    const route = ({ data: of({ aufgabenteilEntity: new AufgabenteilEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabenteilEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AufgabenteilEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AufgabenteilEntityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load aufgabenteilEntity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aufgabenteilEntity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
