import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAufgabenteilEntity } from 'app/shared/model/aufgabenteil-entity.model';
import { AufgabenteilEntityService } from './aufgabenteil-entity.service';
import { AufgabenteilEntityDeleteDialogComponent } from './aufgabenteil-entity-delete-dialog.component';

@Component({
  selector: 'jhi-aufgabenteil-entity',
  templateUrl: './aufgabenteil-entity.component.html',
})
export class AufgabenteilEntityComponent implements OnInit, OnDestroy {
  aufgabenteilEntities?: IAufgabenteilEntity[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected aufgabenteilEntityService: AufgabenteilEntityService,
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
      this.aufgabenteilEntityService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAufgabenteilEntity[]>) => (this.aufgabenteilEntities = res.body || []));
      return;
    }

    this.aufgabenteilEntityService
      .query()
      .subscribe((res: HttpResponse<IAufgabenteilEntity[]>) => (this.aufgabenteilEntities = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAufgabenteilEntities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAufgabenteilEntity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAufgabenteilEntities(): void {
    this.eventSubscriber = this.eventManager.subscribe('aufgabenteilEntityListModification', () => this.loadAll());
  }

  delete(aufgabenteilEntity: IAufgabenteilEntity): void {
    const modalRef = this.modalService.open(AufgabenteilEntityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.aufgabenteilEntity = aufgabenteilEntity;
  }
}
