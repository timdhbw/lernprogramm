import {Component, Input, OnInit} from '@angular/core';
import {AufgabenteilUiDto} from "target/model/aufgabenteil";

@Component({
  selector: 'jhi-aufgabenteil-anzeige',
  templateUrl: './aufgabenteil-anzeige.component.html',
  styleUrls: ['./aufgabenteil-anzeige.component.scss']
})
export class AufgabenteilAnzeigeComponent implements OnInit {

  @Input()
  aufgabenteil: AufgabenteilUiDto | undefined;

  constructor() { }

  ngOnInit(): void {
  }

}
