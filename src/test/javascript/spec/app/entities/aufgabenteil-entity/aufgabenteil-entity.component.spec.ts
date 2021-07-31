import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LernprogrammTestModule } from '../../../test.module';
import { AufgabenteilEntityComponent } from 'app/entities/aufgabenteil-entity/aufgabenteil-entity.component';
import { AufgabenteilEntityService } from 'app/entities/aufgabenteil-entity/aufgabenteil-entity.service';
import { AufgabenteilEntity } from 'app/shared/model/aufgabenteil-entity.model';

describe('Component Tests', () => {
  describe('AufgabenteilEntity Management Component', () => {
    let comp: AufgabenteilEntityComponent;
    let fixture: ComponentFixture<AufgabenteilEntityComponent>;
    let service: AufgabenteilEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabenteilEntityComponent],
      })
        .overrideTemplate(AufgabenteilEntityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AufgabenteilEntityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AufgabenteilEntityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AufgabenteilEntity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.aufgabenteilEntities && comp.aufgabenteilEntities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
