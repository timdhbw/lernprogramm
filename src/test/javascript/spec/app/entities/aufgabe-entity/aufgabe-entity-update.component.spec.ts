import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { AufgabeEntityUpdateComponent } from 'app/entities/aufgabe-entity/aufgabe-entity-update.component';
import { AufgabeEntityService } from 'app/entities/aufgabe-entity/aufgabe-entity.service';
import { AufgabeEntity } from 'app/shared/model/aufgabe-entity.model';

describe('Component Tests', () => {
  describe('AufgabeEntity Management Update Component', () => {
    let comp: AufgabeEntityUpdateComponent;
    let fixture: ComponentFixture<AufgabeEntityUpdateComponent>;
    let service: AufgabeEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabeEntityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AufgabeEntityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AufgabeEntityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AufgabeEntityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AufgabeEntity(123);
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
        const entity = new AufgabeEntity();
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
