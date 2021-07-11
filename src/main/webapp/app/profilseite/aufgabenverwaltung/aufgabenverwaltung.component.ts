import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AufgabeUiDto} from "target/model/aufgabe";
import {FrontendService} from "target/api/frontend.service";

@Component({
  selector: 'jhi-aufgabenverwaltung',
  templateUrl: './aufgabenverwaltung.component.html',
  styleUrls: ['./aufgabenverwaltung.component.scss']
})
export class AufgabenverwaltungComponent implements OnInit {

  eigeneAufgaben: AufgabeUiDto[];

  constructor(private router: Router, private frontendService: FrontendService) {
    this.eigeneAufgaben = [];
    frontendService.getAufgabenByUserId().toPromise().then(aufgaben => this.eigeneAufgaben = aufgaben);
  }

  ngOnInit(): void {
  }

  navigateToNewAufgabe(): void {
    this.router.navigate(["editaufgabe/new"]);
  }
}
