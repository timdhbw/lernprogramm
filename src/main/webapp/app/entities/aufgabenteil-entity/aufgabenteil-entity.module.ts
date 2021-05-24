import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LernprogrammSharedModule } from 'app/shared/shared.module';
import { AufgabenteilEntityComponent } from './aufgabenteil-entity.component';
import { AufgabenteilEntityDetailComponent } from './aufgabenteil-entity-detail.component';
import { AufgabenteilEntityUpdateComponent } from './aufgabenteil-entity-update.component';
import { AufgabenteilEntityDeleteDialogComponent } from './aufgabenteil-entity-delete-dialog.component';
import { aufgabenteilEntityRoute } from './aufgabenteil-entity.route';

@NgModule({
  imports: [LernprogrammSharedModule, RouterModule.forChild(aufgabenteilEntityRoute)],
  declarations: [
    AufgabenteilEntityComponent,
    AufgabenteilEntityDetailComponent,
    AufgabenteilEntityUpdateComponent,
    AufgabenteilEntityDeleteDialogComponent,
  ],
  entryComponents: [AufgabenteilEntityDeleteDialogComponent],
})
export class LernprogrammAufgabenteilEntityModule {}
