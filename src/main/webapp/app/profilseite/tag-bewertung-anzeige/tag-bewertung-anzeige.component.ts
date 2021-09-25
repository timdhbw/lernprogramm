import {Component, Input, OnInit} from '@angular/core';
import {AufgabentagMitSelectUiDto} from "target/model/aufgabentagMitSelect";

@Component({
  selector: 'jhi-tag-bewertung-anzeige',
  templateUrl: './tag-bewertung-anzeige.component.html',
  styleUrls: ['./tag-bewertung-anzeige.component.scss']
})
export class TagBewertungAnzeigeComponent implements OnInit {

  @Input()
  private tagBewerrtungList: AufgabentagMitSelectUiDto[];

  constructor() {
    this.tagBewerrtungList = [];
  }

  ngOnInit(): void {
  }

  get getSortSelectedTags(): AufgabentagMitSelectUiDto[] {
    return this.tagBewerrtungList
      .sort(this.sortTagByBewertung);
  }

  private sortTagByBewertung(a: AufgabentagMitSelectUiDto | undefined, b: AufgabentagMitSelectUiDto | undefined): number {
    if (a?.bewertung !== undefined && b?.bewertung !== undefined) {
      if(a.bewertung > b.bewertung) {
        return 1;
      }
      if(a.bewertung < b.bewertung) {
        return -1;
      }
    }
    return 0;
  }
}
