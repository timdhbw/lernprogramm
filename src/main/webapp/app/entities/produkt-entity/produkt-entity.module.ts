import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LernprogrammSharedModule } from 'app/shared/shared.module';
import { ProduktEntityComponent } from './produkt-entity.component';
import { ProduktEntityDetailComponent } from './produkt-entity-detail.component';
import { ProduktEntityUpdateComponent } from './produkt-entity-update.component';
import { ProduktEntityDeleteDialogComponent } from './produkt-entity-delete-dialog.component';
import { produktEntityRoute } from './produkt-entity.route';

@NgModule({
  imports: [LernprogrammSharedModule, RouterModule.forChild(produktEntityRoute)],
  declarations: [ProduktEntityComponent, ProduktEntityDetailComponent, ProduktEntityUpdateComponent, ProduktEntityDeleteDialogComponent],
  entryComponents: [ProduktEntityDeleteDialogComponent],
})
export class LernprogrammProduktEntityModule {}
