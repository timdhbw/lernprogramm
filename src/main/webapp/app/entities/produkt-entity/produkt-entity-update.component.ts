import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProduktEntity, ProduktEntity } from 'app/shared/model/produkt-entity.model';
import { ProduktEntityService } from './produkt-entity.service';

@Component({
  selector: 'jhi-produkt-entity-update',
  templateUrl: './produkt-entity-update.component.html',
})
export class ProduktEntityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    bewertung: [],
  });

  constructor(protected produktEntityService: ProduktEntityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produktEntity }) => {
      this.updateForm(produktEntity);
    });
  }

  updateForm(produktEntity: IProduktEntity): void {
    this.editForm.patchValue({
      id: produktEntity.id,
      name: produktEntity.name,
      bewertung: produktEntity.bewertung,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produktEntity = this.createFromForm();
    if (produktEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.produktEntityService.update(produktEntity));
    } else {
      this.subscribeToSaveResponse(this.produktEntityService.create(produktEntity));
    }
  }

  private createFromForm(): IProduktEntity {
    return {
      ...new ProduktEntity(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      bewertung: this.editForm.get(['bewertung'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduktEntity>>): void {
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
