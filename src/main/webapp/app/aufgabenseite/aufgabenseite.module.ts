import { NgModule } from '@angular/core';
import { AufgabenseiteComponent } from './aufgabenseite.component';
import {RouterModule} from "@angular/router";
import {AUFGABENSEITE_ROUTE} from "app/aufgabenseite/aufgabenseite.route";
import {LernprogrammSharedModule} from "app/shared/shared.module";



@NgModule({
  declarations: [AufgabenseiteComponent],
  imports: [
    RouterModule.forChild([AUFGABENSEITE_ROUTE]),
    LernprogrammSharedModule
  ]
})
export class LernprogrammAufgabenseiteModule { }
