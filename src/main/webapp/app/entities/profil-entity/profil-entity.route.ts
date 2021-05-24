import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProfilEntity, ProfilEntity } from 'app/shared/model/profil-entity.model';
import { ProfilEntityService } from './profil-entity.service';
import { ProfilEntityComponent } from './profil-entity.component';
import { ProfilEntityDetailComponent } from './profil-entity-detail.component';
import { ProfilEntityUpdateComponent } from './profil-entity-update.component';

@Injectable({ providedIn: 'root' })
export class ProfilEntityResolve implements Resolve<IProfilEntity> {
  constructor(private service: ProfilEntityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfilEntity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((profilEntity: HttpResponse<ProfilEntity>) => {
          if (profilEntity.body) {
            return of(profilEntity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProfilEntity());
  }
}

export const profilEntityRoute: Routes = [
  {
    path: '',
    component: ProfilEntityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.profilEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProfilEntityDetailComponent,
    resolve: {
      profilEntity: ProfilEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.profilEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProfilEntityUpdateComponent,
    resolve: {
      profilEntity: ProfilEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.profilEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProfilEntityUpdateComponent,
    resolve: {
      profilEntity: ProfilEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.profilEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
