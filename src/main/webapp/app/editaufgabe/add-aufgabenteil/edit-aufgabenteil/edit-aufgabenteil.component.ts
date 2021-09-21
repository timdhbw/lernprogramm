import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {AufgabenteilUiDto} from "target/model/aufgabenteil";
import AufgabenteiltypUiDtoEnum = AufgabenteilUiDto.AufgabenteiltypUiDtoEnum;
import {EnumUtil} from "app/shared/util/enum-util";

@Component({
  selector: 'jhi-edit-aufgabenteil',
  templateUrl: './edit-aufgabenteil.component.html',
  styleUrls: ['./edit-aufgabenteil.component.scss']
})
export class EditAufgabenteilComponent implements OnInit {

  @Input()
  speicherBtnText = "Speicher Aufgabenteil";

  @Input()
  aufgabenteil: AufgabenteilUiDto;

  @Output()
  saveClick = new EventEmitter<MouseEvent>();


  constructor(private enumUtil: EnumUtil) {
    this.aufgabenteil = {};
  }

  ngOnInit(): void {
  }

  getAufgabenteilTypList(): AufgabenteiltypUiDtoEnum[] {
    return this.enumUtil.getWerteOfEnum(AufgabenteiltypUiDtoEnum);
  }

  clickOnSave(event: MouseEvent): void {
    this.saveClick.emit(event);
  }

}
