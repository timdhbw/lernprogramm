import { NgModule } from '@angular/core';
import { CreateaufgabeComponent } from './createaufgabe.component';
import {RouterModule} from "@angular/router";
import {LernprogrammSharedModule} from "app/shared/shared.module";
import {CREATEAUFGABE_ROUTE} from "app/createaufgabe/createaufgabe.route";



@NgModule({
  declarations: [CreateaufgabeComponent],
  imports: [
    RouterModule.forChild([CREATEAUFGABE_ROUTE]),
    LernprogrammSharedModule
  ]
})
export class LernprogrammCreateaufgabeModule { }
