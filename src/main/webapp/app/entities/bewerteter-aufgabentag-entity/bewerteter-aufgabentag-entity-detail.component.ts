import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBewerteterAufgabentagEntity } from 'app/shared/model/bewerteter-aufgabentag-entity.model';

@Component({
  selector: 'jhi-bewerteter-aufgabentag-entity-detail',
  templateUrl: './bewerteter-aufgabentag-entity-detail.component.html',
})
export class BewerteterAufgabentagEntityDetailComponent implements OnInit {
  bewerteterAufgabentagEntity: IBewerteterAufgabentagEntity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ bewerteterAufgabentagEntity }) => (this.bewerteterAufgabentagEntity = bewerteterAufgabentagEntity)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
