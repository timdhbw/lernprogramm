import {Component, OnInit} from '@angular/core';
import {AufgabeUiDto} from "target/model/aufgabe";
import {AufgabentagUiDto} from "target/model/aufgabentag";
import {AufgabenteilUiDto} from "target/model/aufgabenteil";
import {ActivatedRoute} from "@angular/router";
import {FrontendService} from "target/api/frontend.service";
import {EnumUtil} from "app/shared/util/enum-util";
import KategorieUiDtoEnum = AufgabeUiDto.KategorieUiDtoEnum;

@Component({
  selector: 'jhi-createaufgabe',
  templateUrl: './editaufgabe.component.html',
  styleUrls: ['./editaufgabe.component.scss']
})
export class EditaufgabeComponent implements OnInit {

  private aufgabe: AufgabeUiDto;

  aufgabeNeuanlage: boolean;

  constructor(private route: ActivatedRoute, private frontendService: FrontendService, private enumUtil: EnumUtil) {
    this.aufgabe = {} as AufgabeUiDto;
    const aufgabeId: string | null = this.route.snapshot.paramMap.get("aufgabeId");
    this.aufgabeNeuanlage = aufgabeId === null || aufgabeId === "new";
    if (this.aufgabeNeuanlage) {
      this.getAufgabeById(undefined);
    } else {
      this.getAufgabeById(Number(aufgabeId));
    }
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

  getKategorieListe(): KategorieUiDtoEnum[] {
    return this.enumUtil.getWerteOfEnum(KategorieUiDtoEnum);
  }

  ngOnInit(): void {
  }

  private getAufgabeById(aufgabeId: number | undefined): void {
    this.frontendService.getAufgabeById(aufgabeId).toPromise().then(aufgabe => this.aufgabe = aufgabe);
  }

  private createNeueAufgabe(): void {
    this.aufgabe.aufgabentitel = "Test";
    this.aufgabe.kategorie = "SOFTWAREENTWICKLUNG";
    this.aufgabe.bewertung = 4;
    this.aufgabe.aufgabentagList = [];
    this.aufgabe.aufgabentagList.push({tag: "Test1"} as AufgabentagUiDto);
    this.aufgabe.aufgabentagList.push({tag: "Test2"} as AufgabentagUiDto);
    this.aufgabe.aufgabentagList.push({tag: "Test3"} as AufgabentagUiDto);
    this.aufgabe.aufgabenteilList = [];
  }
}
