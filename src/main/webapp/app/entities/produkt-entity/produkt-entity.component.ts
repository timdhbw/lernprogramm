import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProduktEntity } from 'app/shared/model/produkt-entity.model';
import { ProduktEntityService } from './produkt-entity.service';
import { ProduktEntityDeleteDialogComponent } from './produkt-entity-delete-dialog.component';

@Component({
  selector: 'jhi-produkt-entity',
  templateUrl: './produkt-entity.component.html',
})
export class ProduktEntityComponent implements OnInit, OnDestroy {
  produktEntities?: IProduktEntity[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected produktEntityService: ProduktEntityService,
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
      this.produktEntityService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IProduktEntity[]>) => (this.produktEntities = res.body || []));
      return;
    }

    this.produktEntityService.query().subscribe((res: HttpResponse<IProduktEntity[]>) => (this.produktEntities = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProduktEntities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProduktEntity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProduktEntities(): void {
    this.eventSubscriber = this.eventManager.subscribe('produktEntityListModification', () => this.loadAll());
  }

  delete(produktEntity: IProduktEntity): void {
    const modalRef = this.modalService.open(ProduktEntityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.produktEntity = produktEntity;
  }
}
