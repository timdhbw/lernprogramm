import {Component, Input, OnInit} from '@angular/core';
import {AufgabeUiDto} from "target/model/aufgabe";
import {AufgabenteilUiDto} from "target/model/aufgabenteil";

@Component({
  selector: 'jhi-aufgabe-anzeige',
  templateUrl: './aufgabe-anzeige.component.html',
  styleUrls: ['./aufgabe-anzeige.component.scss']
})
export class AufgabeAnzeigeComponent implements OnInit {

  @Input()
  aufgabe: AufgabeUiDto | undefined;

  constructor() { }

  ngOnInit(): void {
  }

  getAufgabeteilListSorted(): Array<AufgabenteilUiDto> | undefined {
    this.aufgabe?.aufgabenteilList?.sort((a, b) => {
      if (a !== undefined && b !== undefined) {
        // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
        // @ts-ignore
        return (a.laufenNr > b.laufenNr) ? 1 : -1;
      }
      return 0;
    });
    return this.aufgabe?.aufgabenteilList;
  }

  getAufgabeTagList(): string | undefined {
    return this.aufgabe?.aufgabentagList?.map(tag => tag.tag).join(", ");
  }

  get bewertung(): number {
    if (this.aufgabe?.bewertung !== undefined) {
      return  this.aufgabe.bewertung;
    }
    return 0;
  }

}
