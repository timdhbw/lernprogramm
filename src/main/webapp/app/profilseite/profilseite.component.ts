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

  constructor(private frontendService: FrontendService, private router:Router) {
    this.profil = {} as ProfilUiDto;
    this.fillProfil();
  }

  private fillProfil(): void {
    this.frontendService.getProfil().toPromise().then(value => this.profil = value);
  }

  getName(): string {
    return this.profil.vorname + ' ' + this.profil.nachname;
  }

  navigateToAufgabe(): void {
    this.router.navigate(["aufgabenseite"]);
  }

  ngOnInit(): void {
  }

}
