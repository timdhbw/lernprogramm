import { Component, OnInit } from '@angular/core';
import {AufgabenteilUiDto} from "target/model/aufgabenteil";

@Component({
  selector: 'jhi-createaufgabe',
  templateUrl: './createaufgabe.component.html',
  styleUrls: ['./createaufgabe.component.scss']
})
export class CreateaufgabeComponent implements OnInit {

  aufgabenteilList:AufgabenteilUiDto[];

  constructor() {
    this.aufgabenteilList = [];
  }

  ngOnInit(): void {
  }

}
