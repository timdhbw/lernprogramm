import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LernprogrammSharedModule } from 'app/shared/shared.module';
import { MultipleChoiceAntwortEntityComponent } from './multiple-choice-antwort-entity.component';
import { MultipleChoiceAntwortEntityDetailComponent } from './multiple-choice-antwort-entity-detail.component';
import { MultipleChoiceAntwortEntityUpdateComponent } from './multiple-choice-antwort-entity-update.component';
import { MultipleChoiceAntwortEntityDeleteDialogComponent } from './multiple-choice-antwort-entity-delete-dialog.component';
import { multipleChoiceAntwortEntityRoute } from './multiple-choice-antwort-entity.route';

@NgModule({
  imports: [LernprogrammSharedModule, RouterModule.forChild(multipleChoiceAntwortEntityRoute)],
  declarations: [
    MultipleChoiceAntwortEntityComponent,
    MultipleChoiceAntwortEntityDetailComponent,
    MultipleChoiceAntwortEntityUpdateComponent,
    MultipleChoiceAntwortEntityDeleteDialogComponent,
  ],
  entryComponents: [MultipleChoiceAntwortEntityDeleteDialogComponent],
})
export class LernprogrammMultipleChoiceAntwortEntityModule {}
