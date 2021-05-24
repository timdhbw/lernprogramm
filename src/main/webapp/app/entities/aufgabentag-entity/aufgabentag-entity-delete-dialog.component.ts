import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';
import { AufgabentagEntityService } from './aufgabentag-entity.service';

@Component({
  templateUrl: './aufgabentag-entity-delete-dialog.component.html',
})
export class AufgabentagEntityDeleteDialogComponent {
  aufgabentagEntity?: IAufgabentagEntity;

  constructor(
    protected aufgabentagEntityService: AufgabentagEntityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.aufgabentagEntityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('aufgabentagEntityListModification');
      this.activeModal.close();
    });
  }
}
