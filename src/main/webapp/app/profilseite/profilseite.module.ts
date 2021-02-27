import { NgModule } from '@angular/core';

import { ProfilseiteComponent } from './profilseite.component';
import {RouterModule} from "@angular/router";
import {PROFILSEITE_ROUTE} from "./profilseite.route";


@NgModule({
  declarations: [ProfilseiteComponent],
  imports: [
    RouterModule.forChild([PROFILSEITE_ROUTE])
  ]
})
export class LernprogrammProfilseiteModule { }
