import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { ProduktEntityUpdateComponent } from 'app/entities/produkt-entity/produkt-entity-update.component';
import { ProduktEntityService } from 'app/entities/produkt-entity/produkt-entity.service';
import { ProduktEntity } from 'app/shared/model/produkt-entity.model';

describe('Component Tests', () => {
  describe('ProduktEntity Management Update Component', () => {
    let comp: ProduktEntityUpdateComponent;
    let fixture: ComponentFixture<ProduktEntityUpdateComponent>;
    let service: ProduktEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [ProduktEntityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProduktEntityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProduktEntityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProduktEntityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProduktEntity(123);
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
        const entity = new ProduktEntity();
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
