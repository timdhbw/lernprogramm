import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMultipleChoiceAntwortEntity } from 'app/shared/model/multiple-choice-antwort-entity.model';
import { MultipleChoiceAntwortEntityService } from './multiple-choice-antwort-entity.service';
import { MultipleChoiceAntwortEntityDeleteDialogComponent } from './multiple-choice-antwort-entity-delete-dialog.component';

@Component({
  selector: 'jhi-multiple-choice-antwort-entity',
  templateUrl: './multiple-choice-antwort-entity.component.html',
})
export class MultipleChoiceAntwortEntityComponent implements OnInit, OnDestroy {
  multipleChoiceAntwortEntities?: IMultipleChoiceAntwortEntity[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected multipleChoiceAntwortEntityService: MultipleChoiceAntwortEntityService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.multipleChoiceAntwortEntityService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IMultipleChoiceAntwortEntity[]>) => (this.multipleChoiceAntwortEntities = res.body || []));
      return;
    }

    this.multipleChoiceAntwortEntityService
      .query()
      .subscribe((res: HttpResponse<IMultipleChoiceAntwortEntity[]>) => (this.multipleChoiceAntwortEntities = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMultipleChoiceAntwortEntities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMultipleChoiceAntwortEntity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMultipleChoiceAntwortEntities(): void {
    this.eventSubscriber = this.eventManager.subscribe('multipleChoiceAntwortEntityListModification', () => this.loadAll());
  }

  delete(multipleChoiceAntwortEntity: IMultipleChoiceAntwortEntity): void {
    const modalRef = this.modalService.open(MultipleChoiceAntwortEntityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.multipleChoiceAntwortEntity = multipleChoiceAntwortEntity;
  }
}
