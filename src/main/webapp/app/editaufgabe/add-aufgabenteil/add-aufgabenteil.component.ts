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

  newAufgabenteil: AufgabenteilUiDto | undefined;

  constructor(private enumUtil: EnumUtil) {
    this.showNeuTeil = false;
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
      this.showNeuTeil = false;
      this.aufgabe?.aufgabenteilList?.push(this.newAufgabenteil);
    }
  }
}
