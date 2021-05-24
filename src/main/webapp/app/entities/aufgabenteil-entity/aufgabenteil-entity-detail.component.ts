import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAufgabenteilEntity } from 'app/shared/model/aufgabenteil-entity.model';

@Component({
  selector: 'jhi-aufgabenteil-entity-detail',
  templateUrl: './aufgabenteil-entity-detail.component.html',
})
export class AufgabenteilEntityDetailComponent implements OnInit {
  aufgabenteilEntity: IAufgabenteilEntity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aufgabenteilEntity }) => (this.aufgabenteilEntity = aufgabenteilEntity));
  }

  previousState(): void {
    window.history.back();
  }
}
