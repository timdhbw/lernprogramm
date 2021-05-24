import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProduktEntity, ProduktEntity } from 'app/shared/model/produkt-entity.model';
import { ProduktEntityService } from './produkt-entity.service';
import { ProduktEntityComponent } from './produkt-entity.component';
import { ProduktEntityDetailComponent } from './produkt-entity-detail.component';
import { ProduktEntityUpdateComponent } from './produkt-entity-update.component';

@Injectable({ providedIn: 'root' })
export class ProduktEntityResolve implements Resolve<IProduktEntity> {
  constructor(private service: ProduktEntityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProduktEntity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((produktEntity: HttpResponse<ProduktEntity>) => {
          if (produktEntity.body) {
            return of(produktEntity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProduktEntity());
  }
}

export const produktEntityRoute: Routes = [
  {
    path: '',
    component: ProduktEntityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.produktEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProduktEntityDetailComponent,
    resolve: {
      produktEntity: ProduktEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.produktEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProduktEntityUpdateComponent,
    resolve: {
      produktEntity: ProduktEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.produktEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProduktEntityUpdateComponent,
    resolve: {
      produktEntity: ProduktEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.produktEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
