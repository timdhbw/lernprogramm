import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {AufgabeUiDto} from "target/model/aufgabe";
import {FrontendService} from "target/api/frontend.service";
import {AufgabenteilUiDto} from "target/model/aufgabenteil";

@Component({
  selector: 'jhi-aufgabeabschluss-modal',
  templateUrl: './aufgabeabschluss-modal.component.html',
  styleUrls: ['./aufgabeabschluss-modal.component.scss']
})
export class AufgabeabschlussModalComponent implements OnInit, OnDestroy {

  bewertung: number;

  aufgabe: AufgabeUiDto | undefined;

  ergebnisUser: number | undefined;

  constructor(public activeModal: NgbActiveModal, private frontendService: FrontendService) {
    this.bewertung = 0;
  }

  ngOnInit(): void {
  }

  get ergebnisUserInProzent(): number {
    if (this.ergebnisUser === undefined) {
      return 0;
    } else {
      return (this.ergebnisUser / 5) * 100;
    }
  }

  ngOnDestroy(): void {
    this.sendeBewertungAufgabe();
  }

  private sendeBewertungAufgabe(): void {
    if (this.aufgabe?.aufgabeId && this.bewertung > 0) {
      this.frontendService.setAufgabenbewertung({
        aufgabeId: this.aufgabe.aufgabeId,
        bewertung: this.bewertung
      })
    }
  }

  close(): void {
    this.activeModal.close();
  }

  // private calculateErgebnis(): void {
  //   let count = 0;
  //   this.aufgabe?.aufgabenteilList?.forEach(aufgabenteil => {
  //     if (aufgabenteil.aufgabenteiltyp === 'MULTIPLECHOICE') {
  //       count++;
  //       if (this.isAufgabenteilCorrect(aufgabenteil)) {
  //         if (count === 1) {
  //           this.ergebnisUser = 5;
  //         } else {
  //           this.ergebnisUser = ((this.ergebnisUser * (count - 1)) + 5) / count;
  //         }
  //       } else {
  //         if (count === 1) {
  //           this.ergebnisUser = 1;
  //         } else {
  //           this.ergebnisUser = ((this.ergebnisUser * (count - 1)) + 1) / count;
  //         }
  //       }
  //     }
  //   });
  // }

  isAufgabenteilCorrect(aufgabenteil: AufgabenteilUiDto): boolean {
    return aufgabenteil.multiplechoiceAntwortenList?.findIndex(multi => multi.checked !== multi.checkedRichtig) === -1;
  }

}
