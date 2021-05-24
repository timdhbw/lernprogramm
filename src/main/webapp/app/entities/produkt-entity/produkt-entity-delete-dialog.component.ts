import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProduktEntity } from 'app/shared/model/produkt-entity.model';
import { ProduktEntityService } from './produkt-entity.service';

@Component({
  templateUrl: './produkt-entity-delete-dialog.component.html',
})
export class ProduktEntityDeleteDialogComponent {
  produktEntity?: IProduktEntity;

  constructor(
    protected produktEntityService: ProduktEntityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.produktEntityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('produktEntityListModification');
      this.activeModal.close();
    });
  }
}
