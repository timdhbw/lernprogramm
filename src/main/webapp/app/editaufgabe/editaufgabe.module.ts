import { NgModule } from '@angular/core';
import { EditaufgabeComponent } from './editaufgabe.component';
import {RouterModule} from "@angular/router";
import {LernprogrammSharedModule} from "app/shared/shared.module";
import {EDITAUFGABE_ROUTE} from "app/editaufgabe/editaufgabe.route";



@NgModule({
  declarations: [EditaufgabeComponent],
  imports: [
    RouterModule.forChild([EDITAUFGABE_ROUTE]),
    LernprogrammSharedModule
  ]
})
export class LernprogrammEditaufgabeModule { }
