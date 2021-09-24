import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {MatChipInputEvent} from "@angular/material/chips";
import {AufgabentagMitSelectUiDto} from "target/model/aufgabentagMitSelect";

@Component({
  selector: 'jhi-edit-tag-leiste',
  templateUrl: './edit-tag-leiste.component.html',
  styleUrls: ['./edit-tag-leiste.component.scss']
})
export class EditTagLeisteComponent implements OnInit {

  @Input()
  selectable = false;
  @Input()
  removable = true;
  @Input()
  addOnBlur = true;
  @Input()
  addAble = true;
  @Input()
  aufgabentagList: AufgabentagMitSelectUiDto[] | undefined;
  @Input()
  label: string | undefined;
  @Input()
  subLabel: string | undefined;
  @Output()
  selectClick = new EventEmitter();

  readonly separatorKeysCodes: number[] = [ENTER, COMMA];

  constructor() {
  }

  ngOnInit(): void {
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if (this.aufgabentagList) {
      // Add our tag
      if ((value || '').trim()) {
        this.aufgabentagList.push({tag: value.trim()});
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }
    }
  }

  remove(tag: AufgabentagMitSelectUiDto): void {
    if (this.aufgabentagList) {
      const index = this.aufgabentagList.indexOf(tag);

      if (index >= 0) {
        this.aufgabentagList.splice(index, 1);
      }
    }
  }

  toggleTag(tag: AufgabentagMitSelectUiDto): void {
    if (this.selectable) {
      tag.selected = !tag.selected;
      this.selectClick.emit();
    }
  }
}
