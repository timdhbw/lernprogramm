import {NgModule} from '@angular/core';
import {AufgabenseiteComponent} from './aufgabenseite.component';
import {RouterModule} from "@angular/router";
import {AUFGABENSEITE_ROUTE} from "app/aufgabenseite/aufgabenseite.route";
import {LernprogrammSharedModule} from "app/shared/shared.module";
import {AufgabeabschlussModalComponent} from './aufgabeabschluss-modal/aufgabeabschluss-modal.component';
import {NgRatingBarModule} from "ng-rating-bar";
import {AufgabenteilAnzeigeComponent} from './aufgabenteil-anzeige/aufgabenteil-anzeige.component';
import {AufgabeAnzeigeComponent} from './aufgabe-anzeige/aufgabe-anzeige.component';


@NgModule({
    declarations: [AufgabenseiteComponent, AufgabeabschlussModalComponent, AufgabenteilAnzeigeComponent, AufgabeAnzeigeComponent],
  exports: [
    AufgabenseiteComponent,
    AufgabeAnzeigeComponent
  ],
    imports: [
        RouterModule.forChild([AUFGABENSEITE_ROUTE]),
        LernprogrammSharedModule,
        NgRatingBarModule,
    ]
})
export class LernprogrammAufgabenseiteModule { }
