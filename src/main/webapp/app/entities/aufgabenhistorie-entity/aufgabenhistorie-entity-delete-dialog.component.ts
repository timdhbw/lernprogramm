import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAufgabenhistorieEntity } from 'app/shared/model/aufgabenhistorie-entity.model';
import { AufgabenhistorieEntityService } from './aufgabenhistorie-entity.service';

@Component({
  templateUrl: './aufgabenhistorie-entity-delete-dialog.component.html',
})
export class AufgabenhistorieEntityDeleteDialogComponent {
  aufgabenhistorieEntity?: IAufgabenhistorieEntity;

  constructor(
    protected aufgabenhistorieEntityService: AufgabenhistorieEntityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.aufgabenhistorieEntityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('aufgabenhistorieEntityListModification');
      this.activeModal.close();
    });
  }
}
