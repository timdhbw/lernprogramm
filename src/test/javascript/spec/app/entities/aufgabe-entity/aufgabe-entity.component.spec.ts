import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LernprogrammTestModule } from '../../../test.module';
import { AufgabeEntityComponent } from 'app/entities/aufgabe-entity/aufgabe-entity.component';
import { AufgabeEntityService } from 'app/entities/aufgabe-entity/aufgabe-entity.service';
import { AufgabeEntity } from 'app/shared/model/aufgabe-entity.model';

describe('Component Tests', () => {
  describe('AufgabeEntity Management Component', () => {
    let comp: AufgabeEntityComponent;
    let fixture: ComponentFixture<AufgabeEntityComponent>;
    let service: AufgabeEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabeEntityComponent],
      })
        .overrideTemplate(AufgabeEntityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AufgabeEntityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AufgabeEntityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AufgabeEntity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.aufgabeEntities && comp.aufgabeEntities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
