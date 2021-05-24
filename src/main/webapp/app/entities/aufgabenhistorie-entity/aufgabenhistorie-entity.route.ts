import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAufgabenhistorieEntity, AufgabenhistorieEntity } from 'app/shared/model/aufgabenhistorie-entity.model';
import { AufgabenhistorieEntityService } from './aufgabenhistorie-entity.service';
import { AufgabenhistorieEntityComponent } from './aufgabenhistorie-entity.component';
import { AufgabenhistorieEntityDetailComponent } from './aufgabenhistorie-entity-detail.component';
import { AufgabenhistorieEntityUpdateComponent } from './aufgabenhistorie-entity-update.component';

@Injectable({ providedIn: 'root' })
export class AufgabenhistorieEntityResolve implements Resolve<IAufgabenhistorieEntity> {
  constructor(private service: AufgabenhistorieEntityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAufgabenhistorieEntity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((aufgabenhistorieEntity: HttpResponse<AufgabenhistorieEntity>) => {
          if (aufgabenhistorieEntity.body) {
            return of(aufgabenhistorieEntity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AufgabenhistorieEntity());
  }
}

export const aufgabenhistorieEntityRoute: Routes = [
  {
    path: '',
    component: AufgabenhistorieEntityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabenhistorieEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AufgabenhistorieEntityDetailComponent,
    resolve: {
      aufgabenhistorieEntity: AufgabenhistorieEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabenhistorieEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AufgabenhistorieEntityUpdateComponent,
    resolve: {
      aufgabenhistorieEntity: AufgabenhistorieEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabenhistorieEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AufgabenhistorieEntityUpdateComponent,
    resolve: {
      aufgabenhistorieEntity: AufgabenhistorieEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.aufgabenhistorieEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
