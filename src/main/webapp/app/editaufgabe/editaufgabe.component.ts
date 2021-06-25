import {Component, OnInit} from '@angular/core';
import {AufgabeUiDto} from "target/model/aufgabe";
import {AufgabentagUiDto} from "target/model/aufgabentag";
import {AufgabenteilUiDto} from "target/model/aufgabenteil";

@Component({
  selector: 'jhi-createaufgabe',
  templateUrl: './editaufgabe.component.html',
  styleUrls: ['./editaufgabe.component.scss']
})
export class EditaufgabeComponent implements OnInit {

  private readonly aufgabe: AufgabeUiDto;

  aufgabeNeuanlage: boolean;

  constructor() {
    this.aufgabe = {} as AufgabeUiDto;
    this.aufgabe.aufgabentitel = "Test";
    this.aufgabe.kategorie = "SOFTWAREENTWICKLUNG";
    this.aufgabe.bewertung = 4;
    this.aufgabe.aufgabentagList = [];
    this.aufgabe.aufgabentagList.push({tag: "Test1"} as AufgabentagUiDto);
    this.aufgabe.aufgabentagList.push({tag: "Test2"} as AufgabentagUiDto);
    this.aufgabe.aufgabentagList.push({tag: "Test3"} as AufgabentagUiDto);
    this.aufgabe.aufgabenteilList = [];
    this.aufgabeNeuanlage = false;
  }

  getAufgabe(): AufgabeUiDto {
    return this.aufgabe;
  }

  addAufgabenteil(): void {
    this.aufgabe.aufgabenteilList?.push({
      laufenNr: 2,
      text: "Test"
    } as AufgabenteilUiDto);
  }

  ngOnInit(): void {
  }

}
