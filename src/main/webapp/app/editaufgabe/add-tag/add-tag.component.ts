import {Component, Input, OnInit} from '@angular/core';
import {AufgabentagUiDto} from "target/model/aufgabentag";
import {FrontendService} from "target/api/frontend.service";
import {AufgabeUiDto} from "target/model/aufgabe";

@Component({
  selector: 'jhi-add-tag',
  templateUrl: './add-tag.component.html',
  styleUrls: ['./add-tag.component.scss']
})
export class AddTagComponent implements OnInit {

  showSelect: boolean;

  newTag: AufgabentagUiDto | undefined;

  possibleTags: AufgabentagUiDto[];

  @Input()
  aufgabe: AufgabeUiDto | undefined;

  constructor(private frontendService: FrontendService) {
    this.showSelect = false;
    this.possibleTags = [];
    this.refreshPossibleTags();
  }

  ngOnInit(): void {
  }

  private refreshPossibleTags(): void {
    this.frontendService.getExistingTagList().toPromise().then(tags => {
      this.possibleTags = tags.filter(value => !this.aufgabe?.aufgabentagList?.includes(value));
    })
  }

  addTag(): void {
    if (this.newTag) {
      this.aufgabe?.aufgabentagList?.push(this.newTag);
      this.newTag = undefined;
      this.showSelect = false;
      this.refreshPossibleTags();
    }
  }
}
