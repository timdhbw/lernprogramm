import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMultipleChoiceAntwortEntity } from 'app/shared/model/multiple-choice-antwort-entity.model';
import { MultipleChoiceAntwortEntityService } from './multiple-choice-antwort-entity.service';

@Component({
  templateUrl: './multiple-choice-antwort-entity-delete-dialog.component.html',
})
export class MultipleChoiceAntwortEntityDeleteDialogComponent {
  multipleChoiceAntwortEntity?: IMultipleChoiceAntwortEntity;

  constructor(
    protected multipleChoiceAntwortEntityService: MultipleChoiceAntwortEntityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.multipleChoiceAntwortEntityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('multipleChoiceAntwortEntityListModification');
      this.activeModal.close();
    });
  }
}
