import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAufgabenteilEntity, AufgabenteilEntity } from 'app/shared/model/aufgabenteil-entity.model';
import { AufgabenteilEntityService } from './aufgabenteil-entity.service';
import { IAufgabeEntity } from 'app/shared/model/aufgabe-entity.model';
import { AufgabeEntityService } from 'app/entities/aufgabe-entity/aufgabe-entity.service';

@Component({
  selector: 'jhi-aufgabenteil-entity-update',
  templateUrl: './aufgabenteil-entity-update.component.html',
})
export class AufgabenteilEntityUpdateComponent implements OnInit {
  isSaving = false;
  aufgabeentities: IAufgabeEntity[] = [];

  editForm = this.fb.group({
    id: [],
    laufenNr: [],
    aufgabenteiltyp: [null, [Validators.required]],
    text: [],
    aufgabe: [],
  });

  constructor(
    protected aufgabenteilEntityService: AufgabenteilEntityService,
    protected aufgabeEntityService: AufgabeEntityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aufgabenteilEntity }) => {
      this.updateForm(aufgabenteilEntity);

      this.aufgabeEntityService.query().subscribe((res: HttpResponse<IAufgabeEntity[]>) => (this.aufgabeentities = res.body || []));
    });
  }

  updateForm(aufgabenteilEntity: IAufgabenteilEntity): void {
    this.editForm.patchValue({
      id: aufgabenteilEntity.id,
      laufenNr: aufgabenteilEntity.laufenNr,
      aufgabenteiltyp: aufgabenteilEntity.aufgabenteiltyp,
      text: aufgabenteilEntity.text,
      aufgabe: aufgabenteilEntity.aufgabe,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aufgabenteilEntity = this.createFromForm();
    if (aufgabenteilEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.aufgabenteilEntityService.update(aufgabenteilEntity));
    } else {
      this.subscribeToSaveResponse(this.aufgabenteilEntityService.create(aufgabenteilEntity));
    }
  }

  private createFromForm(): IAufgabenteilEntity {
    return {
      ...new AufgabenteilEntity(),
      id: this.editForm.get(['id'])!.value,
      laufenNr: this.editForm.get(['laufenNr'])!.value,
      aufgabenteiltyp: this.editForm.get(['aufgabenteiltyp'])!.value,
      text: this.editForm.get(['text'])!.value,
      aufgabe: this.editForm.get(['aufgabe'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAufgabenteilEntity>>): void {
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

  trackById(index: number, item: IAufgabeEntity): any {
    return item.id;
  }
}
