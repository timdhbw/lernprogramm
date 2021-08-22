import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMultipleChoiceAntwortEntity } from 'app/shared/model/multiple-choice-antwort-entity.model';

@Component({
  selector: 'jhi-multiple-choice-antwort-entity-detail',
  templateUrl: './multiple-choice-antwort-entity-detail.component.html',
})
export class MultipleChoiceAntwortEntityDetailComponent implements OnInit {
  multipleChoiceAntwortEntity: IMultipleChoiceAntwortEntity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ multipleChoiceAntwortEntity }) => (this.multipleChoiceAntwortEntity = multipleChoiceAntwortEntity)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
