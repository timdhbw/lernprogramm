import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LernprogrammSharedModule } from 'app/shared/shared.module';
import { AufgabenhistorieEntityComponent } from './aufgabenhistorie-entity.component';
import { AufgabenhistorieEntityDetailComponent } from './aufgabenhistorie-entity-detail.component';
import { AufgabenhistorieEntityUpdateComponent } from './aufgabenhistorie-entity-update.component';
import { AufgabenhistorieEntityDeleteDialogComponent } from './aufgabenhistorie-entity-delete-dialog.component';
import { aufgabenhistorieEntityRoute } from './aufgabenhistorie-entity.route';

@NgModule({
  imports: [LernprogrammSharedModule, RouterModule.forChild(aufgabenhistorieEntityRoute)],
  declarations: [
    AufgabenhistorieEntityComponent,
    AufgabenhistorieEntityDetailComponent,
    AufgabenhistorieEntityUpdateComponent,
    AufgabenhistorieEntityDeleteDialogComponent,
  ],
  entryComponents: [AufgabenhistorieEntityDeleteDialogComponent],
})
export class LernprogrammAufgabenhistorieEntityModule {}
