import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBewerteterAufgabentagEntity, BewerteterAufgabentagEntity } from 'app/shared/model/bewerteter-aufgabentag-entity.model';
import { BewerteterAufgabentagEntityService } from './bewerteter-aufgabentag-entity.service';
import { IAufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';
import { AufgabentagEntityService } from 'app/entities/aufgabentag-entity/aufgabentag-entity.service';
import { IProfilEntity } from 'app/shared/model/profil-entity.model';
import { ProfilEntityService } from 'app/entities/profil-entity/profil-entity.service';

type SelectableEntity = IAufgabentagEntity | IProfilEntity;

@Component({
  selector: 'jhi-bewerteter-aufgabentag-entity-update',
  templateUrl: './bewerteter-aufgabentag-entity-update.component.html',
})
export class BewerteterAufgabentagEntityUpdateComponent implements OnInit {
  isSaving = false;
  aufgabentagentities: IAufgabentagEntity[] = [];
  profilentities: IProfilEntity[] = [];

  editForm = this.fb.group({
    id: [],
    bewertung: [],
    aufgabentag: [],
    profil: [],
  });

  constructor(
    protected bewerteterAufgabentagEntityService: BewerteterAufgabentagEntityService,
    protected aufgabentagEntityService: AufgabentagEntityService,
    protected profilEntityService: ProfilEntityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bewerteterAufgabentagEntity }) => {
      this.updateForm(bewerteterAufgabentagEntity);

      this.aufgabentagEntityService
        .query()
        .subscribe((res: HttpResponse<IAufgabentagEntity[]>) => (this.aufgabentagentities = res.body || []));

      this.profilEntityService.query().subscribe((res: HttpResponse<IProfilEntity[]>) => (this.profilentities = res.body || []));
    });
  }

  updateForm(bewerteterAufgabentagEntity: IBewerteterAufgabentagEntity): void {
    this.editForm.patchValue({
      id: bewerteterAufgabentagEntity.id,
      bewertung: bewerteterAufgabentagEntity.bewertung,
      aufgabentag: bewerteterAufgabentagEntity.aufgabentag,
      profil: bewerteterAufgabentagEntity.profil,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bewerteterAufgabentagEntity = this.createFromForm();
    if (bewerteterAufgabentagEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.bewerteterAufgabentagEntityService.update(bewerteterAufgabentagEntity));
    } else {
      this.subscribeToSaveResponse(this.bewerteterAufgabentagEntityService.create(bewerteterAufgabentagEntity));
    }
  }

  private createFromForm(): IBewerteterAufgabentagEntity {
    return {
      ...new BewerteterAufgabentagEntity(),
      id: this.editForm.get(['id'])!.value,
      bewertung: this.editForm.get(['bewertung'])!.value,
      aufgabentag: this.editForm.get(['aufgabentag'])!.value,
      profil: this.editForm.get(['profil'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBewerteterAufgabentagEntity>>): void {
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
