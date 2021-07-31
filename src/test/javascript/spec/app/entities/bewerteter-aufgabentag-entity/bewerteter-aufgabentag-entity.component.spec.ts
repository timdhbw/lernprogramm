import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LernprogrammTestModule } from '../../../test.module';
import { BewerteterAufgabentagEntityComponent } from 'app/entities/bewerteter-aufgabentag-entity/bewerteter-aufgabentag-entity.component';
import { BewerteterAufgabentagEntityService } from 'app/entities/bewerteter-aufgabentag-entity/bewerteter-aufgabentag-entity.service';
import { BewerteterAufgabentagEntity } from 'app/shared/model/bewerteter-aufgabentag-entity.model';

describe('Component Tests', () => {
  describe('BewerteterAufgabentagEntity Management Component', () => {
    let comp: BewerteterAufgabentagEntityComponent;
    let fixture: ComponentFixture<BewerteterAufgabentagEntityComponent>;
    let service: BewerteterAufgabentagEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [BewerteterAufgabentagEntityComponent],
      })
        .overrideTemplate(BewerteterAufgabentagEntityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BewerteterAufgabentagEntityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BewerteterAufgabentagEntityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BewerteterAufgabentagEntity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bewerteterAufgabentagEntities && comp.bewerteterAufgabentagEntities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
