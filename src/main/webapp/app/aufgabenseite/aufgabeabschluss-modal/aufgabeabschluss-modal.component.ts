import { Component, OnInit } from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'jhi-aufgabeabschluss-modal',
  templateUrl: './aufgabeabschluss-modal.component.html',
  styleUrls: ['./aufgabeabschluss-modal.component.scss']
})
export class AufgabeabschlussModalComponent implements OnInit {

  bewertung: number;

  constructor(public activeModal: NgbActiveModal) {
    this.bewertung = 0;
  }

  ngOnInit(): void {
  }

  close(): void {
    this.activeModal.close();
  }
}
