import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProduktEntity } from 'app/shared/model/produkt-entity.model';

@Component({
  selector: 'jhi-produkt-entity-detail',
  templateUrl: './produkt-entity-detail.component.html',
})
export class ProduktEntityDetailComponent implements OnInit {
  produktEntity: IProduktEntity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produktEntity }) => (this.produktEntity = produktEntity));
  }

  previousState(): void {
    window.history.back();
  }
}
