import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {Component, Input, OnInit} from '@angular/core';
import {AufgabeUiDto} from "target/model/aufgabe";
import {MatChipInputEvent} from "@angular/material/chips";

export interface Fruit {
  name: string;
}

@Component({
  selector: 'jhi-edit-tag-leiste',
  templateUrl: './edit-tag-leiste.component.html',
  styleUrls: ['./edit-tag-leiste.component.scss']
})
export class EditTagLeisteComponent implements OnInit {

  selectable = true;
  removable = true;
  addOnBlur = true;
  @Input()
  aufgabe: AufgabeUiDto | undefined;

  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  fruits: Fruit[] = [
    {name: 'Lemon'},
    {name: 'Lime'},
    {name: 'Apple'},
  ];


  constructor() { }

  ngOnInit(): void {
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our fruit
    if ((value || '').trim()) {
      this.fruits.push({name: value.trim()});
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(fruit: Fruit): void {
    const index = this.fruits.indexOf(fruit);

    if (index >= 0) {
      this.fruits.splice(index, 1);
    }
  }

}
