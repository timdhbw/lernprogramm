import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LernprogrammSharedModule } from 'app/shared/shared.module';
import { BewerteterAufgabentagEntityComponent } from './bewerteter-aufgabentag-entity.component';
import { BewerteterAufgabentagEntityDetailComponent } from './bewerteter-aufgabentag-entity-detail.component';
import { BewerteterAufgabentagEntityUpdateComponent } from './bewerteter-aufgabentag-entity-update.component';
import { BewerteterAufgabentagEntityDeleteDialogComponent } from './bewerteter-aufgabentag-entity-delete-dialog.component';
import { bewerteterAufgabentagEntityRoute } from './bewerteter-aufgabentag-entity.route';

@NgModule({
  imports: [LernprogrammSharedModule, RouterModule.forChild(bewerteterAufgabentagEntityRoute)],
  declarations: [
    BewerteterAufgabentagEntityComponent,
    BewerteterAufgabentagEntityDetailComponent,
    BewerteterAufgabentagEntityUpdateComponent,
    BewerteterAufgabentagEntityDeleteDialogComponent,
  ],
  entryComponents: [BewerteterAufgabentagEntityDeleteDialogComponent],
})
export class LernprogrammBewerteterAufgabentagEntityModule {}
