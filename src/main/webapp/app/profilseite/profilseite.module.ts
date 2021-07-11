import { NgModule } from '@angular/core';

import { ProfilseiteComponent } from './profilseite.component';
import {RouterModule} from "@angular/router";
import {PROFILSEITE_ROUTE} from "./profilseite.route";
import {LernprogrammSharedModule} from "app/shared/shared.module";
import { AufgabenverwaltungComponent } from './aufgabenverwaltung/aufgabenverwaltung.component';


@NgModule({
  declarations: [ProfilseiteComponent, AufgabenverwaltungComponent],
  imports: [
    RouterModule.forChild([PROFILSEITE_ROUTE]),
    LernprogrammSharedModule
  ]
})
export class LernprogrammProfilseiteModule { }
