import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAufgabenhistorieEntity } from 'app/shared/model/aufgabenhistorie-entity.model';
import { AufgabenhistorieEntityService } from './aufgabenhistorie-entity.service';
import { AufgabenhistorieEntityDeleteDialogComponent } from './aufgabenhistorie-entity-delete-dialog.component';

@Component({
  selector: 'jhi-aufgabenhistorie-entity',
  templateUrl: './aufgabenhistorie-entity.component.html',
})
export class AufgabenhistorieEntityComponent implements OnInit, OnDestroy {
  aufgabenhistorieEntities?: IAufgabenhistorieEntity[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected aufgabenhistorieEntityService: AufgabenhistorieEntityService,
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
      this.aufgabenhistorieEntityService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAufgabenhistorieEntity[]>) => (this.aufgabenhistorieEntities = res.body || []));
      return;
    }

    this.aufgabenhistorieEntityService
      .query()
      .subscribe((res: HttpResponse<IAufgabenhistorieEntity[]>) => (this.aufgabenhistorieEntities = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAufgabenhistorieEntities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAufgabenhistorieEntity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAufgabenhistorieEntities(): void {
    this.eventSubscriber = this.eventManager.subscribe('aufgabenhistorieEntityListModification', () => this.loadAll());
  }

  delete(aufgabenhistorieEntity: IAufgabenhistorieEntity): void {
    const modalRef = this.modalService.open(AufgabenhistorieEntityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.aufgabenhistorieEntity = aufgabenhistorieEntity;
  }
}
