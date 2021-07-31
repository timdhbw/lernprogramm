import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LernprogrammTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { BewerteterAufgabentagEntityDeleteDialogComponent } from 'app/entities/bewerteter-aufgabentag-entity/bewerteter-aufgabentag-entity-delete-dialog.component';
import { BewerteterAufgabentagEntityService } from 'app/entities/bewerteter-aufgabentag-entity/bewerteter-aufgabentag-entity.service';

describe('Component Tests', () => {
  describe('BewerteterAufgabentagEntity Management Delete Component', () => {
    let comp: BewerteterAufgabentagEntityDeleteDialogComponent;
    let fixture: ComponentFixture<BewerteterAufgabentagEntityDeleteDialogComponent>;
    let service: BewerteterAufgabentagEntityService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [BewerteterAufgabentagEntityDeleteDialogComponent],
      })
        .overrideTemplate(BewerteterAufgabentagEntityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BewerteterAufgabentagEntityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BewerteterAufgabentagEntityService);
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
