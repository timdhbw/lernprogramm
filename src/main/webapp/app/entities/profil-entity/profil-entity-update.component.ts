import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProfilEntity, ProfilEntity } from 'app/shared/model/profil-entity.model';
import { ProfilEntityService } from './profil-entity.service';

@Component({
  selector: 'jhi-profil-entity-update',
  templateUrl: './profil-entity-update.component.html',
})
export class ProfilEntityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    profilId: [null, [Validators.required]],
    vorname: [],
    nachname: [],
  });

  constructor(protected profilEntityService: ProfilEntityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profilEntity }) => {
      this.updateForm(profilEntity);
    });
  }

  updateForm(profilEntity: IProfilEntity): void {
    this.editForm.patchValue({
      id: profilEntity.id,
      profilId: profilEntity.profilId,
      vorname: profilEntity.vorname,
      nachname: profilEntity.nachname,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const profilEntity = this.createFromForm();
    if (profilEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.profilEntityService.update(profilEntity));
    } else {
      this.subscribeToSaveResponse(this.profilEntityService.create(profilEntity));
    }
  }

  private createFromForm(): IProfilEntity {
    return {
      ...new ProfilEntity(),
      id: this.editForm.get(['id'])!.value,
      profilId: this.editForm.get(['profilId'])!.value,
      vorname: this.editForm.get(['vorname'])!.value,
      nachname: this.editForm.get(['nachname'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfilEntity>>): void {
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
