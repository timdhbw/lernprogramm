import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAufgabenhistorieEntity, AufgabenhistorieEntity } from 'app/shared/model/aufgabenhistorie-entity.model';
import { AufgabenhistorieEntityService } from './aufgabenhistorie-entity.service';
import { IProfilEntity } from 'app/shared/model/profil-entity.model';
import { ProfilEntityService } from 'app/entities/profil-entity/profil-entity.service';
import { IAufgabeEntity } from 'app/shared/model/aufgabe-entity.model';
import { AufgabeEntityService } from 'app/entities/aufgabe-entity/aufgabe-entity.service';

type SelectableEntity = IProfilEntity | IAufgabeEntity;

@Component({
  selector: 'jhi-aufgabenhistorie-entity-update',
  templateUrl: './aufgabenhistorie-entity-update.component.html',
})
export class AufgabenhistorieEntityUpdateComponent implements OnInit {
  isSaving = false;
  profilentities: IProfilEntity[] = [];
  aufgabeentities: IAufgabeEntity[] = [];
  datumDp: any;

  editForm = this.fb.group({
    id: [],
    datum: [],
    bewertungsveraenderung: [],
    profil: [],
    aufgabe: [],
  });

  constructor(
    protected aufgabenhistorieEntityService: AufgabenhistorieEntityService,
    protected profilEntityService: ProfilEntityService,
    protected aufgabeEntityService: AufgabeEntityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aufgabenhistorieEntity }) => {
      this.updateForm(aufgabenhistorieEntity);

      this.profilEntityService.query().subscribe((res: HttpResponse<IProfilEntity[]>) => (this.profilentities = res.body || []));

      this.aufgabeEntityService.query().subscribe((res: HttpResponse<IAufgabeEntity[]>) => (this.aufgabeentities = res.body || []));
    });
  }

  updateForm(aufgabenhistorieEntity: IAufgabenhistorieEntity): void {
    this.editForm.patchValue({
      id: aufgabenhistorieEntity.id,
      datum: aufgabenhistorieEntity.datum,
      bewertungsveraenderung: aufgabenhistorieEntity.bewertungsveraenderung,
      profil: aufgabenhistorieEntity.profil,
      aufgabe: aufgabenhistorieEntity.aufgabe,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aufgabenhistorieEntity = this.createFromForm();
    if (aufgabenhistorieEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.aufgabenhistorieEntityService.update(aufgabenhistorieEntity));
    } else {
      this.subscribeToSaveResponse(this.aufgabenhistorieEntityService.create(aufgabenhistorieEntity));
    }
  }

  private createFromForm(): IAufgabenhistorieEntity {
    return {
      ...new AufgabenhistorieEntity(),
      id: this.editForm.get(['id'])!.value,
      datum: this.editForm.get(['datum'])!.value,
      bewertungsveraenderung: this.editForm.get(['bewertungsveraenderung'])!.value,
      profil: this.editForm.get(['profil'])!.value,
      aufgabe: this.editForm.get(['aufgabe'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAufgabenhistorieEntity>>): void {
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
}
