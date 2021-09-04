import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAufgabeEntity, AufgabeEntity } from 'app/shared/model/aufgabe-entity.model';
import { AufgabeEntityService } from './aufgabe-entity.service';
import { IAufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';
import { AufgabentagEntityService } from 'app/entities/aufgabentag-entity/aufgabentag-entity.service';
import { IProfilEntity } from 'app/shared/model/profil-entity.model';
import { ProfilEntityService } from 'app/entities/profil-entity/profil-entity.service';

type SelectableEntity = IAufgabentagEntity | IProfilEntity;

@Component({
  selector: 'jhi-aufgabe-entity-update',
  templateUrl: './aufgabe-entity-update.component.html',
})
export class AufgabeEntityUpdateComponent implements OnInit {
  isSaving = false;
  aufgabentagentities: IAufgabentagEntity[] = [];
  profilentities: IProfilEntity[] = [];

  editForm = this.fb.group({
    id: [],
    aufgabentitel: [],
    kategorie: [null, [Validators.required]],
    aufgabentags: [],
    autor: [],
  });

  constructor(
    protected aufgabeEntityService: AufgabeEntityService,
    protected aufgabentagEntityService: AufgabentagEntityService,
    protected profilEntityService: ProfilEntityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aufgabeEntity }) => {
      this.updateForm(aufgabeEntity);

      this.aufgabentagEntityService
        .query()
        .subscribe((res: HttpResponse<IAufgabentagEntity[]>) => (this.aufgabentagentities = res.body || []));

      this.profilEntityService.query().subscribe((res: HttpResponse<IProfilEntity[]>) => (this.profilentities = res.body || []));
    });
  }

  updateForm(aufgabeEntity: IAufgabeEntity): void {
    this.editForm.patchValue({
      id: aufgabeEntity.id,
      aufgabentitel: aufgabeEntity.aufgabentitel,
      kategorie: aufgabeEntity.kategorie,
      aufgabentags: aufgabeEntity.aufgabentags,
      autor: aufgabeEntity.autor,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aufgabeEntity = this.createFromForm();
    if (aufgabeEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.aufgabeEntityService.update(aufgabeEntity));
    } else {
      this.subscribeToSaveResponse(this.aufgabeEntityService.create(aufgabeEntity));
    }
  }

  private createFromForm(): IAufgabeEntity {
    return {
      ...new AufgabeEntity(),
      id: this.editForm.get(['id'])!.value,
      aufgabentitel: this.editForm.get(['aufgabentitel'])!.value,
      kategorie: this.editForm.get(['kategorie'])!.value,
      aufgabentags: this.editForm.get(['aufgabentags'])!.value,
      autor: this.editForm.get(['autor'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAufgabeEntity>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IAufgabentagEntity[], option: IAufgabentagEntity): IAufgabentagEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
