import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import {AufgabeabschlussModalComponent} from "./aufgabeabschluss-modal.component";
import {AufgabeUiDto} from "target/model/aufgabe";

@Injectable({ providedIn: 'root' })
export class AufgabenabschlussModalService {
  private isOpen = false;

  constructor(private modalService: NgbModal) {}

  open(aufgabe: AufgabeUiDto, ergebnis: number): void {
    if (this.isOpen) {
      return;
    }
    this.isOpen = true;
    const modalRef: NgbModalRef = this.modalService.open(AufgabeabschlussModalComponent);
    modalRef.componentInstance.aufgabe = aufgabe;
    modalRef.componentInstance.ergebnisUser = ergebnis;
    modalRef.result.finally(() => (this.isOpen = false));
  }
}
