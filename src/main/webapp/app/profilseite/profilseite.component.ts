import { Component, OnInit } from '@angular/core';
import {FrontendService} from "target/api/frontend.service";
import {ProfilUiDto} from "target/model/profil";
import {Router} from "@angular/router";

@Component({
  selector: 'jhi-profilseite',
  templateUrl: './profilseite.component.html',
  styleUrls: ['./profilseite.component.scss']
})
export class ProfilseiteComponent implements OnInit {

  profil: ProfilUiDto;

  newAufgabeId: string;

  constructor(private frontendService: FrontendService, private router:Router) {
    this.profil = {} as ProfilUiDto;
    this.newAufgabeId = 'FEHLER';
    this.fillProfil();

  }

  ngOnInit(): void {
    this.refreshNextAufgabe();
  }

  refreshNextAufgabe(): void {
    if (this.profil.allPossibleTagList) {
      this.frontendService.getRandomNextAufgabe(this.profil.allPossibleTagList).toPromise()
        .then(randomId => this.newAufgabeId = randomId);
    }
  }

  private fillProfil(): void {
    this.frontendService.getProfil().toPromise().then(value => this.profil = value);
  }

  getName(): string {
    return this.profil.vorname + ' ' + this.profil.nachname;
  }

  navigateToAufgabe(): void {
    this.router.navigate(["aufgabenseite"], { queryParams: { aufgabeId: this.newAufgabeId } });
  }

  navigateToAufgabeEins(): void {
    this.router.navigate(["aufgabenseite"], { queryParams: { aufgabeId: 1 } });
  }

}
