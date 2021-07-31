import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { BewerteterAufgabentagEntityUpdateComponent } from 'app/entities/bewerteter-aufgabentag-entity/bewerteter-aufgabentag-entity-update.component';
import { BewerteterAufgabentagEntityService } from 'app/entities/bewerteter-aufgabentag-entity/bewerteter-aufgabentag-entity.service';
import { BewerteterAufgabentagEntity } from 'app/shared/model/bewerteter-aufgabentag-entity.model';

describe('Component Tests', () => {
  describe('BewerteterAufgabentagEntity Management Update Component', () => {
    let comp: BewerteterAufgabentagEntityUpdateComponent;
    let fixture: ComponentFixture<BewerteterAufgabentagEntityUpdateComponent>;
    let service: BewerteterAufgabentagEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [BewerteterAufgabentagEntityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BewerteterAufgabentagEntityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BewerteterAufgabentagEntityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BewerteterAufgabentagEntityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BewerteterAufgabentagEntity(123);
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
        const entity = new BewerteterAufgabentagEntity();
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
