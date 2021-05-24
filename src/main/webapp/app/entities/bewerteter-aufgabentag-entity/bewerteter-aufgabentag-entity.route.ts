import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBewerteterAufgabentagEntity, BewerteterAufgabentagEntity } from 'app/shared/model/bewerteter-aufgabentag-entity.model';
import { BewerteterAufgabentagEntityService } from './bewerteter-aufgabentag-entity.service';
import { BewerteterAufgabentagEntityComponent } from './bewerteter-aufgabentag-entity.component';
import { BewerteterAufgabentagEntityDetailComponent } from './bewerteter-aufgabentag-entity-detail.component';
import { BewerteterAufgabentagEntityUpdateComponent } from './bewerteter-aufgabentag-entity-update.component';

@Injectable({ providedIn: 'root' })
export class BewerteterAufgabentagEntityResolve implements Resolve<IBewerteterAufgabentagEntity> {
  constructor(private service: BewerteterAufgabentagEntityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBewerteterAufgabentagEntity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bewerteterAufgabentagEntity: HttpResponse<BewerteterAufgabentagEntity>) => {
          if (bewerteterAufgabentagEntity.body) {
            return of(bewerteterAufgabentagEntity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BewerteterAufgabentagEntity());
  }
}

export const bewerteterAufgabentagEntityRoute: Routes = [
  {
    path: '',
    component: BewerteterAufgabentagEntityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.bewerteterAufgabentagEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BewerteterAufgabentagEntityDetailComponent,
    resolve: {
      bewerteterAufgabentagEntity: BewerteterAufgabentagEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.bewerteterAufgabentagEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BewerteterAufgabentagEntityUpdateComponent,
    resolve: {
      bewerteterAufgabentagEntity: BewerteterAufgabentagEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.bewerteterAufgabentagEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BewerteterAufgabentagEntityUpdateComponent,
    resolve: {
      bewerteterAufgabentagEntity: BewerteterAufgabentagEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.bewerteterAufgabentagEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
