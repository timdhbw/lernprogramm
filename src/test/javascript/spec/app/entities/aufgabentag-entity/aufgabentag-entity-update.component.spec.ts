import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { AufgabentagEntityUpdateComponent } from 'app/entities/aufgabentag-entity/aufgabentag-entity-update.component';
import { AufgabentagEntityService } from 'app/entities/aufgabentag-entity/aufgabentag-entity.service';
import { AufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';

describe('Component Tests', () => {
  describe('AufgabentagEntity Management Update Component', () => {
    let comp: AufgabentagEntityUpdateComponent;
    let fixture: ComponentFixture<AufgabentagEntityUpdateComponent>;
    let service: AufgabentagEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabentagEntityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AufgabentagEntityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AufgabentagEntityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AufgabentagEntityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AufgabentagEntity(123);
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
        const entity = new AufgabentagEntity();
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
