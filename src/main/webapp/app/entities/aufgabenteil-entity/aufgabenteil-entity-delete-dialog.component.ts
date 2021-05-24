import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAufgabenteilEntity } from 'app/shared/model/aufgabenteil-entity.model';
import { AufgabenteilEntityService } from './aufgabenteil-entity.service';

@Component({
  templateUrl: './aufgabenteil-entity-delete-dialog.component.html',
})
export class AufgabenteilEntityDeleteDialogComponent {
  aufgabenteilEntity?: IAufgabenteilEntity;

  constructor(
    protected aufgabenteilEntityService: AufgabenteilEntityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.aufgabenteilEntityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('aufgabenteilEntityListModification');
      this.activeModal.close();
    });
  }
}
