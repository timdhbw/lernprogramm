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

  @Input()
  aufgabenverwaltungLabel: string | undefined;

  @Input()
  initialShownAufgaben: number | undefined;

  showMore = false;

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  navigateToNewAufgabe(): void {
    this.router.navigate(["editaufgabe/new"]);
  }

  navigateToEditAufgabe(aufgabeId: number | undefined): void {
    if (aufgabeId !== undefined) {
      this.router.navigate(["editaufgabe/" + aufgabeId]);
    }
  }

  get eigeneAufgabenBySize(): AufgabeUiDto[] {
    if (this.eigeneAufgaben) {
      if (!this.showMore && this.initialShownAufgaben) {
        return this.eigeneAufgaben.slice(0, this.initialShownAufgaben);
      }
      return this.eigeneAufgaben;
    }
    return [];
  }

  get showButton(): boolean {
    if (this.eigeneAufgaben?.length && this.initialShownAufgaben) {
      return this.eigeneAufgaben.length > this.initialShownAufgaben;
    }
    return false;
  }
}
