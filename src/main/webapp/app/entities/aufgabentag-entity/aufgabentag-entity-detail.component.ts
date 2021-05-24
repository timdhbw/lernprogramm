import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';

@Component({
  selector: 'jhi-aufgabentag-entity-detail',
  templateUrl: './aufgabentag-entity-detail.component.html',
})
export class AufgabentagEntityDetailComponent implements OnInit {
  aufgabentagEntity: IAufgabentagEntity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aufgabentagEntity }) => (this.aufgabentagEntity = aufgabentagEntity));
  }

  previousState(): void {
    window.history.back();
  }
}
