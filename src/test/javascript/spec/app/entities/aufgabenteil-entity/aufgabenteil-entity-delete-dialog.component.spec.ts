import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LernprogrammTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { AufgabenteilEntityDeleteDialogComponent } from 'app/entities/aufgabenteil-entity/aufgabenteil-entity-delete-dialog.component';
import { AufgabenteilEntityService } from 'app/entities/aufgabenteil-entity/aufgabenteil-entity.service';

describe('Component Tests', () => {
  describe('AufgabenteilEntity Management Delete Component', () => {
    let comp: AufgabenteilEntityDeleteDialogComponent;
    let fixture: ComponentFixture<AufgabenteilEntityDeleteDialogComponent>;
    let service: AufgabenteilEntityService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabenteilEntityDeleteDialogComponent],
      })
        .overrideTemplate(AufgabenteilEntityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AufgabenteilEntityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AufgabenteilEntityService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
