import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LernprogrammTestModule } from '../../../test.module';
import { AufgabentagEntityComponent } from 'app/entities/aufgabentag-entity/aufgabentag-entity.component';
import { AufgabentagEntityService } from 'app/entities/aufgabentag-entity/aufgabentag-entity.service';
import { AufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';

describe('Component Tests', () => {
  describe('AufgabentagEntity Management Component', () => {
    let comp: AufgabentagEntityComponent;
    let fixture: ComponentFixture<AufgabentagEntityComponent>;
    let service: AufgabentagEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabentagEntityComponent],
      })
        .overrideTemplate(AufgabentagEntityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AufgabentagEntityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AufgabentagEntityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AufgabentagEntity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.aufgabentagEntities && comp.aufgabentagEntities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
