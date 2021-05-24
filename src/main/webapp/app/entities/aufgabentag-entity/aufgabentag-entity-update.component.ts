import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAufgabentagEntity, AufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';
import { AufgabentagEntityService } from './aufgabentag-entity.service';

@Component({
  selector: 'jhi-aufgabentag-entity-update',
  templateUrl: './aufgabentag-entity-update.component.html',
})
export class AufgabentagEntityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tag: [null, [Validators.required]],
    text: [],
  });

  constructor(
    protected aufgabentagEntityService: AufgabentagEntityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aufgabentagEntity }) => {
      this.updateForm(aufgabentagEntity);
    });
  }

  updateForm(aufgabentagEntity: IAufgabentagEntity): void {
    this.editForm.patchValue({
      id: aufgabentagEntity.id,
      tag: aufgabentagEntity.tag,
      text: aufgabentagEntity.text,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aufgabentagEntity = this.createFromForm();
    if (aufgabentagEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.aufgabentagEntityService.update(aufgabentagEntity));
    } else {
      this.subscribeToSaveResponse(this.aufgabentagEntityService.create(aufgabentagEntity));
    }
  }

  private createFromForm(): IAufgabentagEntity {
    return {
      ...new AufgabentagEntity(),
      id: this.editForm.get(['id'])!.value,
      tag: this.editForm.get(['tag'])!.value,
      text: this.editForm.get(['text'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAufgabentagEntity>>): void {
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
}
