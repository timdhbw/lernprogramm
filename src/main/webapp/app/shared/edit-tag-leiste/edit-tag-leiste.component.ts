import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {Component, Input, OnInit} from '@angular/core';
import {AufgabeUiDto} from "target/model/aufgabe";
import {MatChipInputEvent} from "@angular/material/chips";
import {AufgabentagUiDto} from "target/model/aufgabentag";

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
  aufgabe: AufgabeUiDto | undefined;

  readonly separatorKeysCodes: number[] = [ENTER, COMMA];

  constructor() {
  }

  ngOnInit(): void {
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if (this.aufgabe?.aufgabentagList) {
      // Add our tag
      if ((value || '').trim()) {
        this.aufgabe.aufgabentagList.push({tag: value.trim()});
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }
    }
  }

  remove(tag: AufgabentagUiDto): void {
    if (this.aufgabe?.aufgabentagList) {
      const index = this.aufgabe.aufgabentagList.indexOf(tag);

      if (index >= 0) {
        this.aufgabe.aufgabentagList.splice(index, 1);
      }
    }
  }

}
