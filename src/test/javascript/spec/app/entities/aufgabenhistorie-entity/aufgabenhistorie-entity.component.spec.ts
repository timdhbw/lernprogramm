import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LernprogrammTestModule } from '../../../test.module';
import { AufgabenhistorieEntityComponent } from 'app/entities/aufgabenhistorie-entity/aufgabenhistorie-entity.component';
import { AufgabenhistorieEntityService } from 'app/entities/aufgabenhistorie-entity/aufgabenhistorie-entity.service';
import { AufgabenhistorieEntity } from 'app/shared/model/aufgabenhistorie-entity.model';

describe('Component Tests', () => {
  describe('AufgabenhistorieEntity Management Component', () => {
    let comp: AufgabenhistorieEntityComponent;
    let fixture: ComponentFixture<AufgabenhistorieEntityComponent>;
    let service: AufgabenhistorieEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabenhistorieEntityComponent],
      })
        .overrideTemplate(AufgabenhistorieEntityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AufgabenhistorieEntityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AufgabenhistorieEntityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AufgabenhistorieEntity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.aufgabenhistorieEntities && comp.aufgabenhistorieEntities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
