import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';
import { AufgabentagEntityService } from './aufgabentag-entity.service';
import { AufgabentagEntityDeleteDialogComponent } from './aufgabentag-entity-delete-dialog.component';

@Component({
  selector: 'jhi-aufgabentag-entity',
  templateUrl: './aufgabentag-entity.component.html',
})
export class AufgabentagEntityComponent implements OnInit, OnDestroy {
  aufgabentagEntities?: IAufgabentagEntity[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected aufgabentagEntityService: AufgabentagEntityService,
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
      this.aufgabentagEntityService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAufgabentagEntity[]>) => (this.aufgabentagEntities = res.body || []));
      return;
    }

    this.aufgabentagEntityService
      .query()
      .subscribe((res: HttpResponse<IAufgabentagEntity[]>) => (this.aufgabentagEntities = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAufgabentagEntities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAufgabentagEntity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAufgabentagEntities(): void {
    this.eventSubscriber = this.eventManager.subscribe('aufgabentagEntityListModification', () => this.loadAll());
  }

  delete(aufgabentagEntity: IAufgabentagEntity): void {
    const modalRef = this.modalService.open(AufgabentagEntityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.aufgabentagEntity = aufgabentagEntity;
  }
}
