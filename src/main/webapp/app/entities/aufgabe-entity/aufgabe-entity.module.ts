import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LernprogrammSharedModule } from 'app/shared/shared.module';
import { AufgabeEntityComponent } from './aufgabe-entity.component';
import { AufgabeEntityDetailComponent } from './aufgabe-entity-detail.component';
import { AufgabeEntityUpdateComponent } from './aufgabe-entity-update.component';
import { AufgabeEntityDeleteDialogComponent } from './aufgabe-entity-delete-dialog.component';
import { aufgabeEntityRoute } from './aufgabe-entity.route';

@NgModule({
  imports: [LernprogrammSharedModule, RouterModule.forChild(aufgabeEntityRoute)],
  declarations: [AufgabeEntityComponent, AufgabeEntityDetailComponent, AufgabeEntityUpdateComponent, AufgabeEntityDeleteDialogComponent],
  entryComponents: [AufgabeEntityDeleteDialogComponent],
})
export class LernprogrammAufgabeEntityModule {}
