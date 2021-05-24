import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfilEntity } from 'app/shared/model/profil-entity.model';

@Component({
  selector: 'jhi-profil-entity-detail',
  templateUrl: './profil-entity-detail.component.html',
})
export class ProfilEntityDetailComponent implements OnInit {
  profilEntity: IProfilEntity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profilEntity }) => (this.profilEntity = profilEntity));
  }

  previousState(): void {
    window.history.back();
  }
}
