import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBewerteterAufgabentagEntity } from 'app/shared/model/bewerteter-aufgabentag-entity.model';
import { BewerteterAufgabentagEntityService } from './bewerteter-aufgabentag-entity.service';
import { BewerteterAufgabentagEntityDeleteDialogComponent } from './bewerteter-aufgabentag-entity-delete-dialog.component';

@Component({
  selector: 'jhi-bewerteter-aufgabentag-entity',
  templateUrl: './bewerteter-aufgabentag-entity.component.html',
})
export class BewerteterAufgabentagEntityComponent implements OnInit, OnDestroy {
  bewerteterAufgabentagEntities?: IBewerteterAufgabentagEntity[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected bewerteterAufgabentagEntityService: BewerteterAufgabentagEntityService,
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
      this.bewerteterAufgabentagEntityService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IBewerteterAufgabentagEntity[]>) => (this.bewerteterAufgabentagEntities = res.body || []));
      return;
    }

    this.bewerteterAufgabentagEntityService
      .query()
      .subscribe((res: HttpResponse<IBewerteterAufgabentagEntity[]>) => (this.bewerteterAufgabentagEntities = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBewerteterAufgabentagEntities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBewerteterAufgabentagEntity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBewerteterAufgabentagEntities(): void {
    this.eventSubscriber = this.eventManager.subscribe('bewerteterAufgabentagEntityListModification', () => this.loadAll());
  }

  delete(bewerteterAufgabentagEntity: IBewerteterAufgabentagEntity): void {
    const modalRef = this.modalService.open(BewerteterAufgabentagEntityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bewerteterAufgabentagEntity = bewerteterAufgabentagEntity;
  }
}
