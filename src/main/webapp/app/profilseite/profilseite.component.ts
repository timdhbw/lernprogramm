import { Component, OnInit } from '@angular/core';
import {FrontendService} from "target/api/frontend.service";
import {ProfilUiDto} from "target/model/profil";
import {Router} from "@angular/router";
import {AufgabentagMitSelectUiDto} from "target/model/aufgabentagMitSelect";

@Component({
  selector: 'jhi-profilseite',
  templateUrl: './profilseite.component.html',
  styleUrls: ['./profilseite.component.scss']
})
export class ProfilseiteComponent {

  profil: ProfilUiDto;

  newAufgabeId: string;

  constructor(private frontendService: FrontendService, private router:Router) {
    this.profil = {} as ProfilUiDto;
    this.newAufgabeId = '';
    this.fillProfil();

  }

  refreshNextAufgabe(): void {
    if (this.profil.allTagsBewertetList) {
      this.frontendService.getRandomNextAufgabe(this.profil.allTagsBewertetList).toPromise()
        .then(randomId => this.newAufgabeId = randomId)
        .catch(err => {
          // Probleme bei Parsen von Datum, deshalb Text aus Error Message
          this.newAufgabeId = err.error.text
        });
    }
  }

  get allTagsBewertetList(): AufgabentagMitSelectUiDto[] {
    if (this.profil.allTagsBewertetList) {
      return this.profil.allTagsBewertetList;
    }
    return [];
  }

  private fillProfil(): void {
    this.frontendService.getProfil().toPromise().then(value => {
      this.profil = value;
      this.refreshNextAufgabe();
    });
  }

  getName(): string {
    return this.profil.vorname + ' ' + this.profil.nachname;
  }

  get aufgabeIdnichtGefunden(): boolean {
    return this.newAufgabeId === '';
  }

  get aufgabeHeuteAbgeschlossen(): boolean {
    return this.newAufgabeId.length > 13 && this.newAufgabeId.startsWith('abgeschlossen');
  }

  get isAufgabeIdValid(): boolean {
    return !this.aufgabeIdnichtGefunden && !this.aufgabeHeuteAbgeschlossen;
  }

  navigateToAufgabe(): void {
    if (this.isAufgabeIdValid) {
      this.router.navigate(["aufgabenseite"], {queryParams: {aufgabeId: this.newAufgabeId}});
    }
  }

  navigateToAufgabeEins(): void {
    this.router.navigate(["aufgabenseite"], { queryParams: { aufgabeId: 1 } });
  }

}
