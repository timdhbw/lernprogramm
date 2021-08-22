import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMultipleChoiceAntwortEntity, MultipleChoiceAntwortEntity } from 'app/shared/model/multiple-choice-antwort-entity.model';
import { MultipleChoiceAntwortEntityService } from './multiple-choice-antwort-entity.service';
import { MultipleChoiceAntwortEntityComponent } from './multiple-choice-antwort-entity.component';
import { MultipleChoiceAntwortEntityDetailComponent } from './multiple-choice-antwort-entity-detail.component';
import { MultipleChoiceAntwortEntityUpdateComponent } from './multiple-choice-antwort-entity-update.component';

@Injectable({ providedIn: 'root' })
export class MultipleChoiceAntwortEntityResolve implements Resolve<IMultipleChoiceAntwortEntity> {
  constructor(private service: MultipleChoiceAntwortEntityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMultipleChoiceAntwortEntity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((multipleChoiceAntwortEntity: HttpResponse<MultipleChoiceAntwortEntity>) => {
          if (multipleChoiceAntwortEntity.body) {
            return of(multipleChoiceAntwortEntity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MultipleChoiceAntwortEntity());
  }
}

export const multipleChoiceAntwortEntityRoute: Routes = [
  {
    path: '',
    component: MultipleChoiceAntwortEntityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.multipleChoiceAntwortEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MultipleChoiceAntwortEntityDetailComponent,
    resolve: {
      multipleChoiceAntwortEntity: MultipleChoiceAntwortEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.multipleChoiceAntwortEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MultipleChoiceAntwortEntityUpdateComponent,
    resolve: {
      multipleChoiceAntwortEntity: MultipleChoiceAntwortEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.multipleChoiceAntwortEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MultipleChoiceAntwortEntityUpdateComponent,
    resolve: {
      multipleChoiceAntwortEntity: MultipleChoiceAntwortEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lernprogrammApp.multipleChoiceAntwortEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
