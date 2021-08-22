import { Component, OnInit } from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'jhi-aufgabeabschluss-modal',
  templateUrl: './aufgabeabschluss-modal.component.html',
  styleUrls: ['./aufgabeabschluss-modal.component.scss']
})
export class AufgabeabschlussModalComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  close(): void {
    this.activeModal.close();
  }
}
