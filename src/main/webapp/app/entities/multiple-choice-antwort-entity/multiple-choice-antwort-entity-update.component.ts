import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMultipleChoiceAntwortEntity, MultipleChoiceAntwortEntity } from 'app/shared/model/multiple-choice-antwort-entity.model';
import { MultipleChoiceAntwortEntityService } from './multiple-choice-antwort-entity.service';
import { IAufgabenteilEntity } from 'app/shared/model/aufgabenteil-entity.model';
import { AufgabenteilEntityService } from 'app/entities/aufgabenteil-entity/aufgabenteil-entity.service';

@Component({
  selector: 'jhi-multiple-choice-antwort-entity-update',
  templateUrl: './multiple-choice-antwort-entity-update.component.html',
})
export class MultipleChoiceAntwortEntityUpdateComponent implements OnInit {
  isSaving = false;
  aufgabenteilentities: IAufgabenteilEntity[] = [];

  editForm = this.fb.group({
    id: [],
    checked: [],
    checkedRichtig: [],
    label: [],
    aufgabenteil: [],
  });

  constructor(
    protected multipleChoiceAntwortEntityService: MultipleChoiceAntwortEntityService,
    protected aufgabenteilEntityService: AufgabenteilEntityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ multipleChoiceAntwortEntity }) => {
      this.updateForm(multipleChoiceAntwortEntity);

      this.aufgabenteilEntityService
        .query()
        .subscribe((res: HttpResponse<IAufgabenteilEntity[]>) => (this.aufgabenteilentities = res.body || []));
    });
  }

  updateForm(multipleChoiceAntwortEntity: IMultipleChoiceAntwortEntity): void {
    this.editForm.patchValue({
      id: multipleChoiceAntwortEntity.id,
      checked: multipleChoiceAntwortEntity.checked,
      checkedRichtig: multipleChoiceAntwortEntity.checkedRichtig,
      label: multipleChoiceAntwortEntity.label,
      aufgabenteil: multipleChoiceAntwortEntity.aufgabenteil,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const multipleChoiceAntwortEntity = this.createFromForm();
    if (multipleChoiceAntwortEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.multipleChoiceAntwortEntityService.update(multipleChoiceAntwortEntity));
    } else {
      this.subscribeToSaveResponse(this.multipleChoiceAntwortEntityService.create(multipleChoiceAntwortEntity));
    }
  }

  private createFromForm(): IMultipleChoiceAntwortEntity {
    return {
      ...new MultipleChoiceAntwortEntity(),
      id: this.editForm.get(['id'])!.value,
      checked: this.editForm.get(['checked'])!.value,
      checkedRichtig: this.editForm.get(['checkedRichtig'])!.value,
      label: this.editForm.get(['label'])!.value,
      aufgabenteil: this.editForm.get(['aufgabenteil'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMultipleChoiceAntwortEntity>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IAufgabenteilEntity): any {
    return item.id;
  }
}
