import { Component, OnInit } from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {AufgabeUiDto} from "target/model/aufgabe";
import {FrontendService} from "target/api/frontend.service";

@Component({
  selector: 'jhi-aufgabeabschluss-modal',
  templateUrl: './aufgabeabschluss-modal.component.html',
  styleUrls: ['./aufgabeabschluss-modal.component.scss']
})
export class AufgabeabschlussModalComponent implements OnInit {

  bewertung: number;

  aufgabe: AufgabeUiDto | undefined;

  private ergebnisUser: number;

  constructor(public activeModal: NgbActiveModal, private frontendService: FrontendService) {
    this.bewertung = 0;
    // TODO ergebnis User aus Aufgabe errechnen
    this.ergebnisUser = 3;
  }

  ngOnInit(): void {
  }

  close(): void {
    if (this.aufgabe?.aufgabeId) {
      this.frontendService.getAufgabeIstAbgeschlossen(this.aufgabe?.aufgabeId, this.ergebnisUser, this.bewertung)
    }
    this.activeModal.close();
  }

}
