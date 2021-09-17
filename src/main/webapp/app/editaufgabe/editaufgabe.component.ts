import {Component, OnInit} from '@angular/core';
import {AufgabeUiDto} from "target/model/aufgabe";
import {AufgabentagUiDto} from "target/model/aufgabentag";
import {AufgabenteilUiDto} from "target/model/aufgabenteil";
import {ActivatedRoute, Router} from "@angular/router";
import {FrontendService} from "target/api/frontend.service";
import {EnumUtil} from "app/shared/util/enum-util";
import KategorieUiDtoEnum = AufgabeUiDto.KategorieUiDtoEnum;
import {JhiAlertService} from "ng-jhipster";

@Component({
  selector: 'jhi-createaufgabe',
  templateUrl: './editaufgabe.component.html',
  styleUrls: ['./editaufgabe.component.scss']
})
export class EditaufgabeComponent implements OnInit {

  private aufgabe: AufgabeUiDto;

  aufgabeNeuanlage: boolean;

  constructor(private route: ActivatedRoute, private frontendService: FrontendService, private router: Router, private enumUtil: EnumUtil, private alertService: JhiAlertService) {
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

  removeTag(tag: AufgabentagUiDto): void {
    const index: number | undefined = this.aufgabe.aufgabentagList?.indexOf(tag);
    if (index !== undefined && index !== -1) {
      this.aufgabe.aufgabentagList?.splice(index, 1);
    }
  }

  getNumberPlusOne(number: number | undefined): number {
    if (number) {
      return number + 1;
    }
    return 0;
  }

  saveAufgabe(): void {
    this.frontendService.saveAufgabe(this.aufgabe).toPromise().then(() => {
      this.alertService.success("Aufgabe erfolgreich gespeichert!")
      this.router.navigate(['profilseite']);
    })
  .catch(() => this.alertService.error("Aufgabe konnte nicht gespeichert werden!"));
  }

  abbrechen(): void {
    this.router.navigate(['profilseite']);
  }
}
