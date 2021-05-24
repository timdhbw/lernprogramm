import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProfilEntity } from 'app/shared/model/profil-entity.model';
import { ProfilEntityService } from './profil-entity.service';
import { ProfilEntityDeleteDialogComponent } from './profil-entity-delete-dialog.component';

@Component({
  selector: 'jhi-profil-entity',
  templateUrl: './profil-entity.component.html',
})
export class ProfilEntityComponent implements OnInit, OnDestroy {
  profilEntities?: IProfilEntity[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected profilEntityService: ProfilEntityService,
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
      this.profilEntityService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IProfilEntity[]>) => (this.profilEntities = res.body || []));
      return;
    }

    this.profilEntityService.query().subscribe((res: HttpResponse<IProfilEntity[]>) => (this.profilEntities = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProfilEntities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProfilEntity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProfilEntities(): void {
    this.eventSubscriber = this.eventManager.subscribe('profilEntityListModification', () => this.loadAll());
  }

  delete(profilEntity: IProfilEntity): void {
    const modalRef = this.modalService.open(ProfilEntityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.profilEntity = profilEntity;
  }
}
