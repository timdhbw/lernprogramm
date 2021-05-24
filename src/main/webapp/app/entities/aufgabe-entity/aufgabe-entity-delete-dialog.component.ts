import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAufgabeEntity } from 'app/shared/model/aufgabe-entity.model';
import { AufgabeEntityService } from './aufgabe-entity.service';

@Component({
  templateUrl: './aufgabe-entity-delete-dialog.component.html',
})
export class AufgabeEntityDeleteDialogComponent {
  aufgabeEntity?: IAufgabeEntity;

  constructor(
    protected aufgabeEntityService: AufgabeEntityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.aufgabeEntityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('aufgabeEntityListModification');
      this.activeModal.close();
    });
  }
}
