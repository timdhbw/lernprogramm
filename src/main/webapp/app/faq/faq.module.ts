import { NgModule } from '@angular/core';
import { FaqComponent } from './faq.component';
import {RouterModule} from "@angular/router";
import {LernprogrammSharedModule} from "app/shared/shared.module";
import {FAQ_ROUTE} from "app/faq/faq.route";



@NgModule({
  declarations: [FaqComponent],
  imports: [
    RouterModule.forChild([FAQ_ROUTE]),
    LernprogrammSharedModule
  ]
})
export class LernprogrammFaqModule { }
