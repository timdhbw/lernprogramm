import { NgModule } from '@angular/core';
import { EditaufgabeComponent } from './editaufgabe.component';
import {RouterModule} from "@angular/router";
import {LernprogrammSharedModule} from "app/shared/shared.module";
import {EDITAUFGABE_ROUTE} from "app/editaufgabe/editaufgabe.route";
import { AddAufgabenteilComponent } from './add-aufgabenteil/add-aufgabenteil.component';
import { EditMultipleChoiceComponent } from './add-aufgabenteil/edit-multiple-choice/edit-multiple-choice.component';
import {LernprogrammAufgabenseiteModule} from "app/aufgabenseite/aufgabenseite.module";
import { EditAufgabenteilComponent } from './add-aufgabenteil/edit-aufgabenteil/edit-aufgabenteil.component';



@NgModule({
  declarations: [EditaufgabeComponent, AddAufgabenteilComponent, EditMultipleChoiceComponent, EditAufgabenteilComponent],
  exports: [
  ],
  imports: [
    RouterModule.forChild([EDITAUFGABE_ROUTE]),
    LernprogrammSharedModule,
    LernprogrammAufgabenseiteModule
  ]
})
export class LernprogrammEditaufgabeModule { }
