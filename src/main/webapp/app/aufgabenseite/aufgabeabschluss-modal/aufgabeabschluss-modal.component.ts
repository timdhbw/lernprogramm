import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {AufgabeUiDto} from "target/model/aufgabe";
import {FrontendService} from "target/api/frontend.service";
import {AufgabenteilUiDto} from "target/model/aufgabenteil";

@Component({
  selector: 'jhi-aufgabeabschluss-modal',
  templateUrl: './aufgabeabschluss-modal.component.html',
  styleUrls: ['./aufgabeabschluss-modal.component.scss']
})
export class AufgabeabschlussModalComponent implements OnInit {

  bewertung: number;

  aufgabe: AufgabeUiDto | undefined;

  ergebnisUser: number;

  constructor(public activeModal: NgbActiveModal, private frontendService: FrontendService) {
    this.bewertung = 0;
    this.ergebnisUser = 5;
    this.calculateErgebnis();
  }

  ngOnInit(): void {
  }

  close(): void {
    if (this.aufgabe?.aufgabeId) {
      this.frontendService.getAufgabeIstAbgeschlossen(this.aufgabe?.aufgabeId, this.ergebnisUser, this.bewertung)
    }
    this.activeModal.close();
  }

  private calculateErgebnis(): void {
    let count = 0;
    this.aufgabe?.aufgabenteilList?.forEach(aufgabenteil => {
      if (aufgabenteil.aufgabenteiltyp === 'MULTIPLECHOICE') {
        count++;
        if (this.isAufgabenteilCorrect(aufgabenteil)) {
          if (count === 1) {
            this.ergebnisUser = 5;
          } else {
            this.ergebnisUser = ((this.ergebnisUser * (count - 1)) + 5) / count;
          }
        } else {
          if (count === 1) {
            this.ergebnisUser = 1;
          } else {
            this.ergebnisUser = ((this.ergebnisUser * (count - 1)) + 1) / count;
          }
        }
      }
    });
  }

  isAufgabenteilCorrect(aufgabenteil: AufgabenteilUiDto): boolean {
    return aufgabenteil.multiplechoiceAntwortenList?.findIndex(multi => multi.checked !== multi.checkedRichtig) === -1;
  }

}
