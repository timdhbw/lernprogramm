import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfilEntity } from 'app/shared/model/profil-entity.model';
import { ProfilEntityService } from './profil-entity.service';

@Component({
  templateUrl: './profil-entity-delete-dialog.component.html',
})
export class ProfilEntityDeleteDialogComponent {
  profilEntity?: IProfilEntity;

  constructor(
    protected profilEntityService: ProfilEntityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.profilEntityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('profilEntityListModification');
      this.activeModal.close();
    });
  }
}
