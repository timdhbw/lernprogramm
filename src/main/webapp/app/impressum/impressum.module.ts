import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ImpressumComponent} from './impressum.component';
import {RouterModule} from "@angular/router";
import {IMPRESSUM_ROUTE} from "./impressum.route";


@NgModule({
  declarations: [ImpressumComponent],
  imports: [
    RouterModule.forChild([IMPRESSUM_ROUTE]),
    CommonModule
  ]
})
export class LernprogrammImpressumModule { }
