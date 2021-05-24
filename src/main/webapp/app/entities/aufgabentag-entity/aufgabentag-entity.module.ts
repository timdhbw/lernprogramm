import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LernprogrammSharedModule } from 'app/shared/shared.module';
import { AufgabentagEntityComponent } from './aufgabentag-entity.component';
import { AufgabentagEntityDetailComponent } from './aufgabentag-entity-detail.component';
import { AufgabentagEntityUpdateComponent } from './aufgabentag-entity-update.component';
import { AufgabentagEntityDeleteDialogComponent } from './aufgabentag-entity-delete-dialog.component';
import { aufgabentagEntityRoute } from './aufgabentag-entity.route';

@NgModule({
  imports: [LernprogrammSharedModule, RouterModule.forChild(aufgabentagEntityRoute)],
  declarations: [
    AufgabentagEntityComponent,
    AufgabentagEntityDetailComponent,
    AufgabentagEntityUpdateComponent,
    AufgabentagEntityDeleteDialogComponent,
  ],
  entryComponents: [AufgabentagEntityDeleteDialogComponent],
})
export class LernprogrammAufgabentagEntityModule {}
