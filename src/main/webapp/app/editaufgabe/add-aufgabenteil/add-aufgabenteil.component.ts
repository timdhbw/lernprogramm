import {Component, Input, OnInit} from '@angular/core';
import {AufgabeUiDto} from "target/model/aufgabe";
import {AufgabenteilUiDto} from "target/model/aufgabenteil";
import {EnumUtil} from "app/shared/util/enum-util";
import AufgabenteiltypUiDtoEnum = AufgabenteilUiDto.AufgabenteiltypUiDtoEnum;

@Component({
  selector: 'jhi-add-aufgabenteil',
  templateUrl: './add-aufgabenteil.component.html',
  styleUrls: ['./add-aufgabenteil.component.scss']
})
export class AddAufgabenteilComponent implements OnInit {

  @Input()
  aufgabe: AufgabeUiDto | undefined;

  @Input()
  laufendeNr: number | undefined;

  showNeuTeil: boolean;

  newAufgabenteil: AufgabenteilUiDto;

  constructor(private enumUtil: EnumUtil) {
    this.showNeuTeil = false;
    this.newAufgabenteil = {} as AufgabenteilUiDto;
  }

  ngOnInit(): void {
  }

  getAufgabenteilTypList(): AufgabenteiltypUiDtoEnum[] {
    return this.enumUtil.getWerteOfEnum(AufgabenteiltypUiDtoEnum);
  }

  neuerAufgabenteil(): void {
    this.showNeuTeil = true;
    this.newAufgabenteil = {} as AufgabenteilUiDto;
  }

  saveNewAufgabenteil(): void {
    if (this.newAufgabenteil !== undefined) {
      if (this.laufendeNr === 0) {
        this.laufendeNr = 1;
      }
      this.newAufgabenteil.laufenNr = this.laufendeNr;
      this.erhoeheHoehereLaufendeNummern(this.laufendeNr);
      this.showNeuTeil = false;
      this.aufgabe?.aufgabenteilList?.push(this.newAufgabenteil);
      this.orderAufgabenteile();
      this.newAufgabenteil = {} as AufgabenteilUiDto;
    }
  }

  private erhoeheHoehereLaufendeNummern(laufNr: number | undefined): void {
    this.aufgabe?.aufgabenteilList?.forEach(teil => {
      if (teil.laufenNr && laufNr && teil.laufenNr >= laufNr) {
        teil.laufenNr++;
      }
    })
  }

  private orderAufgabenteile(): void {
    if (this.aufgabe?.aufgabenteilList) {
      this.aufgabe.aufgabenteilList.sort((a,b) => {
        if (a.laufenNr && b.laufenNr) {
         return  a.laufenNr - b.laufenNr
        }
        return 0;
      });
    }
  }
}
