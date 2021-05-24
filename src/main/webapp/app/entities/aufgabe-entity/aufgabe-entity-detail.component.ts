import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAufgabeEntity } from 'app/shared/model/aufgabe-entity.model';

@Component({
  selector: 'jhi-aufgabe-entity-detail',
  templateUrl: './aufgabe-entity-detail.component.html',
})
export class AufgabeEntityDetailComponent implements OnInit {
  aufgabeEntity: IAufgabeEntity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aufgabeEntity }) => (this.aufgabeEntity = aufgabeEntity));
  }

  previousState(): void {
    window.history.back();
  }
}
