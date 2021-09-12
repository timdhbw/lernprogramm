import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AufgabeUiDto} from "target/model/aufgabe";

@Component({
  selector: 'jhi-aufgabenverwaltung',
  templateUrl: './aufgabenverwaltung.component.html',
  styleUrls: ['./aufgabenverwaltung.component.scss']
})
export class AufgabenverwaltungComponent implements OnInit {

  @Input()
  eigeneAufgaben: AufgabeUiDto[] | undefined;

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  navigateToNewAufgabe(): void {
    this.router.navigate(["editaufgabe/new"]);
  }
}
