import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAufgabenhistorieEntity } from 'app/shared/model/aufgabenhistorie-entity.model';

@Component({
  selector: 'jhi-aufgabenhistorie-entity-detail',
  templateUrl: './aufgabenhistorie-entity-detail.component.html',
})
export class AufgabenhistorieEntityDetailComponent implements OnInit {
  aufgabenhistorieEntity: IAufgabenhistorieEntity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aufgabenhistorieEntity }) => (this.aufgabenhistorieEntity = aufgabenhistorieEntity));
  }

  previousState(): void {
    window.history.back();
  }
}
