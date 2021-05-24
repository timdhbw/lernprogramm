import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LernprogrammSharedModule } from 'app/shared/shared.module';
import { ProfilEntityComponent } from './profil-entity.component';
import { ProfilEntityDetailComponent } from './profil-entity-detail.component';
import { ProfilEntityUpdateComponent } from './profil-entity-update.component';
import { ProfilEntityDeleteDialogComponent } from './profil-entity-delete-dialog.component';
import { profilEntityRoute } from './profil-entity.route';

@NgModule({
  imports: [LernprogrammSharedModule, RouterModule.forChild(profilEntityRoute)],
  declarations: [ProfilEntityComponent, ProfilEntityDetailComponent, ProfilEntityUpdateComponent, ProfilEntityDeleteDialogComponent],
  entryComponents: [ProfilEntityDeleteDialogComponent],
})
export class LernprogrammProfilEntityModule {}
