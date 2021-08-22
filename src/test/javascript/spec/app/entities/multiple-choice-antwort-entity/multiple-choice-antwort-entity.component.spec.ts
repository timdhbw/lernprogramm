import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LernprogrammTestModule } from '../../../test.module';
import { MultipleChoiceAntwortEntityComponent } from 'app/entities/multiple-choice-antwort-entity/multiple-choice-antwort-entity.component';
import { MultipleChoiceAntwortEntityService } from 'app/entities/multiple-choice-antwort-entity/multiple-choice-antwort-entity.service';
import { MultipleChoiceAntwortEntity } from 'app/shared/model/multiple-choice-antwort-entity.model';

describe('Component Tests', () => {
  describe('MultipleChoiceAntwortEntity Management Component', () => {
    let comp: MultipleChoiceAntwortEntityComponent;
    let fixture: ComponentFixture<MultipleChoiceAntwortEntityComponent>;
    let service: MultipleChoiceAntwortEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [MultipleChoiceAntwortEntityComponent],
      })
        .overrideTemplate(MultipleChoiceAntwortEntityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MultipleChoiceAntwortEntityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MultipleChoiceAntwortEntityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MultipleChoiceAntwortEntity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.multipleChoiceAntwortEntities && comp.multipleChoiceAntwortEntities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
