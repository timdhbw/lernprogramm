import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAufgabeEntity, AufgabeEntity } from 'app/shared/model/aufgabe-entity.model';
import { AufgabeEntityService } from './aufgabe-entity.service';
import { AufgabeEntityComponent } from './aufgabe-entity.component';
import { AufgabeEntityDetailComponent } from './aufgabe-entity-detail.component';
import { AufgabeEntityUpdateComponent } from './aufgabe-entity-update.component';

@Injectable({ providedIn: 'root' })
export class AufgabeEntityResolve implements Resolve<IAufgabeEntity> {
  constructor(private service: AufgabeEntityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAufgabeEntity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((aufgabeEntity: HttpResponse<AufgabeEntity>) => {
          if (aufgabeEntity.body) {
            return of(aufgabeEntity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AufgabeEntity());
  }
}

export const aufgabeEntityRoute: Routes = [
  {
    path: '',
    component: AufgabeEntityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabeEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AufgabeEntityDetailComponent,
    resolve: {
      aufgabeEntity: AufgabeEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabeEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AufgabeEntityUpdateComponent,
    resolve: {
      aufgabeEntity: AufgabeEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabeEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AufgabeEntityUpdateComponent,
    resolve: {
      aufgabeEntity: AufgabeEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabeEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
