import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import {AufgabeabschlussModalComponent} from "./aufgabeabschluss-modal.component";

@Injectable({ providedIn: 'root' })
export class AufgabenabschlussModalService {
  private isOpen = false;

  constructor(private modalService: NgbModal) {}

  open(): void {
    if (this.isOpen) {
      return;
    }
    this.isOpen = true;
    const modalRef: NgbModalRef = this.modalService.open(AufgabeabschlussModalComponent);
    modalRef.result.finally(() => (this.isOpen = false));
  }
}
