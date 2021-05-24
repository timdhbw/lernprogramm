import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAufgabenhistorieEntity } from 'app/shared/model/aufgabenhistorie-entity.model';

type EntityResponseType = HttpResponse<IAufgabenhistorieEntity>;
type EntityArrayResponseType = HttpResponse<IAufgabenhistorieEntity[]>;

@Injectable({ providedIn: 'root' })
export class AufgabenhistorieEntityService {
  public resourceUrl = SERVER_API_URL + 'api/aufgabenhistorie-entities';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/aufgabenhistorie-entities';

  constructor(protected http: HttpClient) {}

  create(aufgabenhistorieEntity: IAufgabenhistorieEntity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aufgabenhistorieEntity);
    return this.http
      .post<IAufgabenhistorieEntity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(aufgabenhistorieEntity: IAufgabenhistorieEntity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aufgabenhistorieEntity);
    return this.http
      .put<IAufgabenhistorieEntity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAufgabenhistorieEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAufgabenhistorieEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAufgabenhistorieEntity[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(aufgabenhistorieEntity: IAufgabenhistorieEntity): IAufgabenhistorieEntity {
    const copy: IAufgabenhistorieEntity = Object.assign({}, aufgabenhistorieEntity, {
      datum:
        aufgabenhistorieEntity.datum && aufgabenhistorieEntity.datum.isValid()
          ? aufgabenhistorieEntity.datum.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datum = res.body.datum ? moment(res.body.datum) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((aufgabenhistorieEntity: IAufgabenhistorieEntity) => {
        aufgabenhistorieEntity.datum = aufgabenhistorieEntity.datum ? moment(aufgabenhistorieEntity.datum) : undefined;
      });
    }
    return res;
  }
}
