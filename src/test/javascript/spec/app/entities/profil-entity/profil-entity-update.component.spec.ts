import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { ProfilEntityUpdateComponent } from 'app/entities/profil-entity/profil-entity-update.component';
import { ProfilEntityService } from 'app/entities/profil-entity/profil-entity.service';
import { ProfilEntity } from 'app/shared/model/profil-entity.model';

describe('Component Tests', () => {
  describe('ProfilEntity Management Update Component', () => {
    let comp: ProfilEntityUpdateComponent;
    let fixture: ComponentFixture<ProfilEntityUpdateComponent>;
    let service: ProfilEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [ProfilEntityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProfilEntityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfilEntityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfilEntityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfilEntity(123);
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
        const entity = new ProfilEntity();
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
