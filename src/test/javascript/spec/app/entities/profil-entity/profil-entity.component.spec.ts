import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LernprogrammTestModule } from '../../../test.module';
import { ProfilEntityComponent } from 'app/entities/profil-entity/profil-entity.component';
import { ProfilEntityService } from 'app/entities/profil-entity/profil-entity.service';
import { ProfilEntity } from 'app/shared/model/profil-entity.model';

describe('Component Tests', () => {
  describe('ProfilEntity Management Component', () => {
    let comp: ProfilEntityComponent;
    let fixture: ComponentFixture<ProfilEntityComponent>;
    let service: ProfilEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [ProfilEntityComponent],
      })
        .overrideTemplate(ProfilEntityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfilEntityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfilEntityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProfilEntity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.profilEntities && comp.profilEntities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
