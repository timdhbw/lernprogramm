import {Component, Input, OnInit} from '@angular/core';
import {AufgabenteilUiDto} from "target/model/aufgabenteil";

@Component({
  selector: 'jhi-edit-multiple-choice',
  templateUrl: './edit-multiple-choice.component.html',
  styleUrls: ['./edit-multiple-choice.component.scss']
})
export class EditMultipleChoiceComponent implements OnInit {

  @Input()
  aufgabenteil: AufgabenteilUiDto | undefined;

  showNeuMultiple: boolean;

  constructor() {
    this.showNeuMultiple = false;
  }

  ngOnInit(): void {
  }

}
