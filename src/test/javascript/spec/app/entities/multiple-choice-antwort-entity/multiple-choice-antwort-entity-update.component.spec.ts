import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { MultipleChoiceAntwortEntityUpdateComponent } from 'app/entities/multiple-choice-antwort-entity/multiple-choice-antwort-entity-update.component';
import { MultipleChoiceAntwortEntityService } from 'app/entities/multiple-choice-antwort-entity/multiple-choice-antwort-entity.service';
import { MultipleChoiceAntwortEntity } from 'app/shared/model/multiple-choice-antwort-entity.model';

describe('Component Tests', () => {
  describe('MultipleChoiceAntwortEntity Management Update Component', () => {
    let comp: MultipleChoiceAntwortEntityUpdateComponent;
    let fixture: ComponentFixture<MultipleChoiceAntwortEntityUpdateComponent>;
    let service: MultipleChoiceAntwortEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [MultipleChoiceAntwortEntityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MultipleChoiceAntwortEntityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MultipleChoiceAntwortEntityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MultipleChoiceAntwortEntityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MultipleChoiceAntwortEntity(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MultipleChoiceAntwortEntity();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
