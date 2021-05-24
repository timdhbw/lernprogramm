import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAufgabentagEntity, AufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';
import { AufgabentagEntityService } from './aufgabentag-entity.service';
import { AufgabentagEntityComponent } from './aufgabentag-entity.component';
import { AufgabentagEntityDetailComponent } from './aufgabentag-entity-detail.component';
import { AufgabentagEntityUpdateComponent } from './aufgabentag-entity-update.component';

@Injectable({ providedIn: 'root' })
export class AufgabentagEntityResolve implements Resolve<IAufgabentagEntity> {
  constructor(private service: AufgabentagEntityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAufgabentagEntity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((aufgabentagEntity: HttpResponse<AufgabentagEntity>) => {
          if (aufgabentagEntity.body) {
            return of(aufgabentagEntity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AufgabentagEntity());
  }
}

export const aufgabentagEntityRoute: Routes = [
  {
    path: '',
    component: AufgabentagEntityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabentagEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AufgabentagEntityDetailComponent,
    resolve: {
      aufgabentagEntity: AufgabentagEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabentagEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AufgabentagEntityUpdateComponent,
    resolve: {
      aufgabentagEntity: AufgabentagEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabentagEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AufgabentagEntityUpdateComponent,
    resolve: {
      aufgabentagEntity: AufgabentagEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabentagEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
