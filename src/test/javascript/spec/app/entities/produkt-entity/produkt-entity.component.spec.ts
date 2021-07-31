import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LernprogrammTestModule } from '../../../test.module';
import { ProduktEntityComponent } from 'app/entities/produkt-entity/produkt-entity.component';
import { ProduktEntityService } from 'app/entities/produkt-entity/produkt-entity.service';
import { ProduktEntity } from 'app/shared/model/produkt-entity.model';

describe('Component Tests', () => {
  describe('ProduktEntity Management Component', () => {
    let comp: ProduktEntityComponent;
    let fixture: ComponentFixture<ProduktEntityComponent>;
    let service: ProduktEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [ProduktEntityComponent],
      })
        .overrideTemplate(ProduktEntityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProduktEntityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProduktEntityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProduktEntity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.produktEntities && comp.produktEntities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
