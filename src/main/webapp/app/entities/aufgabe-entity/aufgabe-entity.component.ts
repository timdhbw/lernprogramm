import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAufgabeEntity } from 'app/shared/model/aufgabe-entity.model';
import { AufgabeEntityService } from './aufgabe-entity.service';
import { AufgabeEntityDeleteDialogComponent } from './aufgabe-entity-delete-dialog.component';

@Component({
  selector: 'jhi-aufgabe-entity',
  templateUrl: './aufgabe-entity.component.html',
})
export class AufgabeEntityComponent implements OnInit, OnDestroy {
  aufgabeEntities?: IAufgabeEntity[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected aufgabeEntityService: AufgabeEntityService,
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
      this.aufgabeEntityService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAufgabeEntity[]>) => (this.aufgabeEntities = res.body || []));
      return;
    }

    this.aufgabeEntityService.query().subscribe((res: HttpResponse<IAufgabeEntity[]>) => (this.aufgabeEntities = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAufgabeEntities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAufgabeEntity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAufgabeEntities(): void {
    this.eventSubscriber = this.eventManager.subscribe('aufgabeEntityListModification', () => this.loadAll());
  }

  delete(aufgabeEntity: IAufgabeEntity): void {
    const modalRef = this.modalService.open(AufgabeEntityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.aufgabeEntity = aufgabeEntity;
  }
}
