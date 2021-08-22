import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { MultipleChoiceAntwortEntityDetailComponent } from 'app/entities/multiple-choice-antwort-entity/multiple-choice-antwort-entity-detail.component';
import { MultipleChoiceAntwortEntity } from 'app/shared/model/multiple-choice-antwort-entity.model';

describe('Component Tests', () => {
  describe('MultipleChoiceAntwortEntity Management Detail Component', () => {
    let comp: MultipleChoiceAntwortEntityDetailComponent;
    let fixture: ComponentFixture<MultipleChoiceAntwortEntityDetailComponent>;
    const route = ({ data: of({ multipleChoiceAntwortEntity: new MultipleChoiceAntwortEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [MultipleChoiceAntwortEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MultipleChoiceAntwortEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MultipleChoiceAntwortEntityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load multipleChoiceAntwortEntity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.multipleChoiceAntwortEntity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
