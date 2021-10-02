import {Component, Input, OnInit} from '@angular/core';
import {AufgabenteilUiDto} from "target/model/aufgabenteil";
import {MultipleChoiceAntwortUiDto} from "target/model/multipleChoiceAntwort";

@Component({
  selector: 'jhi-edit-multiple-choice',
  templateUrl: './edit-multiple-choice.component.html',
  styleUrls: ['./edit-multiple-choice.component.scss']
})
export class EditMultipleChoiceComponent implements OnInit {

  @Input()
  aufgabenteil: AufgabenteilUiDto | undefined;

  newMultiTeil: MultipleChoiceAntwortUiDto;

  showNeuMultiple = false;

  constructor() {
    this.newMultiTeil = {} as MultipleChoiceAntwortUiDto;
  }

  addNewMultiToAufgabe(): void {
    if (this.aufgabenteil) {
      if (this.aufgabenteil.multiplechoiceAntwortenList === undefined) {
        this.aufgabenteil.multiplechoiceAntwortenList = [];
      }
      this.aufgabenteil.multiplechoiceAntwortenList.push(this.newMultiTeil);
      this.newMultiTeil = {} as MultipleChoiceAntwortUiDto;
      this.showNeuMultiple = false;
    }
  }

  ngOnInit(): void {
  }

  removeMultiPleChoice(multipleChoice: MultipleChoiceAntwortUiDto): void {
    if (this.aufgabenteil) {
      if (this.aufgabenteil.multiplechoiceAntwortenList) {
        const index = this.aufgabenteil.multiplechoiceAntwortenList.indexOf(multipleChoice);
        if (index > -1) {
          this.aufgabenteil.multiplechoiceAntwortenList.splice(index, 1);
        }
      }
    }
  }
}
