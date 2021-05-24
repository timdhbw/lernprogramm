import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAufgabenteilEntity, AufgabenteilEntity } from 'app/shared/model/aufgabenteil-entity.model';
import { AufgabenteilEntityService } from './aufgabenteil-entity.service';
import { AufgabenteilEntityComponent } from './aufgabenteil-entity.component';
import { AufgabenteilEntityDetailComponent } from './aufgabenteil-entity-detail.component';
import { AufgabenteilEntityUpdateComponent } from './aufgabenteil-entity-update.component';

@Injectable({ providedIn: 'root' })
export class AufgabenteilEntityResolve implements Resolve<IAufgabenteilEntity> {
  constructor(private service: AufgabenteilEntityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAufgabenteilEntity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((aufgabenteilEntity: HttpResponse<AufgabenteilEntity>) => {
          if (aufgabenteilEntity.body) {
            return of(aufgabenteilEntity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AufgabenteilEntity());
  }
}

export const aufgabenteilEntityRoute: Routes = [
  {
    path: '',
    component: AufgabenteilEntityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabenteilEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AufgabenteilEntityDetailComponent,
    resolve: {
      aufgabenteilEntity: AufgabenteilEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabenteilEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AufgabenteilEntityUpdateComponent,
    resolve: {
      aufgabenteilEntity: AufgabenteilEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabenteilEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AufgabenteilEntityUpdateComponent,
    resolve: {
      aufgabenteilEntity: AufgabenteilEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabenteilEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
