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

  @Input()
  bewertungLabel: string | undefined;

  @Input()
  initialShowenSize: number | undefined;

  showMore = false;

  constructor() {
    this.tagBewerrtungList = [];
  }

  ngOnInit(): void {
  }

  get getSortSelectedTags(): AufgabentagMitSelectUiDto[] {
    const list = this.tagBewerrtungList
      .sort(this.sortTagByBewertung);
    if (this.initialShowenSize && this.initialShowenSize < list.length && !this.showMore) {
      return list.slice(0, this.initialShowenSize);
    }
    return list;
  }

  get showButton(): boolean {
    if (this.initialShowenSize) {
      return this.tagBewerrtungList.length > this.initialShowenSize;
    }
    return false;
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
