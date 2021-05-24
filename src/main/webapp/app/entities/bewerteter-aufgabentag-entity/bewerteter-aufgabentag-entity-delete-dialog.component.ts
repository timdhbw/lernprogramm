import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBewerteterAufgabentagEntity } from 'app/shared/model/bewerteter-aufgabentag-entity.model';
import { BewerteterAufgabentagEntityService } from './bewerteter-aufgabentag-entity.service';

@Component({
  templateUrl: './bewerteter-aufgabentag-entity-delete-dialog.component.html',
})
export class BewerteterAufgabentagEntityDeleteDialogComponent {
  bewerteterAufgabentagEntity?: IBewerteterAufgabentagEntity;

  constructor(
    protected bewerteterAufgabentagEntityService: BewerteterAufgabentagEntityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bewerteterAufgabentagEntityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bewerteterAufgabentagEntityListModification');
      this.activeModal.close();
    });
  }
}
