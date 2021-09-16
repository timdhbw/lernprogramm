import { NgModule } from '@angular/core';
import { EditaufgabeComponent } from './editaufgabe.component';
import {RouterModule} from "@angular/router";
import {LernprogrammSharedModule} from "app/shared/shared.module";
import {EDITAUFGABE_ROUTE} from "app/editaufgabe/editaufgabe.route";
import { AddTagComponent } from './add-tag/add-tag.component';
import { AddAufgabenteilComponent } from './add-aufgabenteil/add-aufgabenteil.component';
import { EditMultipleChoiceComponent } from './add-aufgabenteil/edit-multiple-choice/edit-multiple-choice.component';
import {LernprogrammAufgabenseiteModule} from "app/aufgabenseite/aufgabenseite.module";



@NgModule({
  declarations: [EditaufgabeComponent, AddTagComponent, AddAufgabenteilComponent, EditMultipleChoiceComponent],
  exports: [
  ],
  imports: [
    RouterModule.forChild([EDITAUFGABE_ROUTE]),
    LernprogrammSharedModule,
    LernprogrammAufgabenseiteModule
  ]
})
export class LernprogrammEditaufgabeModule { }
