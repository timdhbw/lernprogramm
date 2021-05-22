import { NgModule } from '@angular/core';

import { ProfilseiteComponent } from './profilseite.component';
import {RouterModule} from "@angular/router";
import {PROFILSEITE_ROUTE} from "./profilseite.route";
import {LernprogrammSharedModule} from "app/shared/shared.module";


@NgModule({
  declarations: [ProfilseiteComponent],
  imports: [
    RouterModule.forChild([PROFILSEITE_ROUTE]),
    LernprogrammSharedModule
  ]
})
export class LernprogrammProfilseiteModule { }
