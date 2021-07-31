import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LernprogrammTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { AufgabenhistorieEntityDeleteDialogComponent } from 'app/entities/aufgabenhistorie-entity/aufgabenhistorie-entity-delete-dialog.component';
import { AufgabenhistorieEntityService } from 'app/entities/aufgabenhistorie-entity/aufgabenhistorie-entity.service';

describe('Component Tests', () => {
  describe('AufgabenhistorieEntity Management Delete Component', () => {
    let comp: AufgabenhistorieEntityDeleteDialogComponent;
    let fixture: ComponentFixture<AufgabenhistorieEntityDeleteDialogComponent>;
    let service: AufgabenhistorieEntityService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [AufgabenhistorieEntityDeleteDialogComponent],
      })
        .overrideTemplate(AufgabenhistorieEntityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AufgabenhistorieEntityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AufgabenhistorieEntityService);
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
