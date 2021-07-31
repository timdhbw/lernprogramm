import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { AufgabenhistorieEntityUpdateComponent } from 'app/entities/aufgabenhistorie-entity/aufgabenhistorie-entity-update.component';
import { AufgabenhistorieEntityService } from 'app/entities/aufgabenhistorie-entity/aufgabenhistorie-entity.service';
import { AufgabenhistorieEntity } from 'app/shared/model/aufgabenhistorie-entity.model';

describe('Component Tests', () => {
  describe('AufgabenhistorieEntity Management Update Component', () => {
    let comp: AufgabenhistorieEntityUpdateComponent;
    let fixture: ComponentFixture<AufgabenhistorieEntityUpdateComponent>;
    let service: AufgabenhistorieEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabenhistorieEntityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AufgabenhistorieEntityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AufgabenhistorieEntityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AufgabenhistorieEntityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AufgabenhistorieEntity(123);
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
        const entity = new AufgabenhistorieEntity();
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
