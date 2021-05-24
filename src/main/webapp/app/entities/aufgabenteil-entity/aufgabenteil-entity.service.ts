import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAufgabenteilEntity } from 'app/shared/model/aufgabenteil-entity.model';

type EntityResponseType = HttpResponse<IAufgabenteilEntity>;
type EntityArrayResponseType = HttpResponse<IAufgabenteilEntity[]>;

@Injectable({ providedIn: 'root' })
export class AufgabenteilEntityService {
  public resourceUrl = SERVER_API_URL + 'api/aufgabenteil-entities';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/aufgabenteil-entities';

  constructor(protected http: HttpClient) {}

  create(aufgabenteilEntity: IAufgabenteilEntity): Observable<EntityResponseType> {
    return this.http.post<IAufgabenteilEntity>(this.resourceUrl, aufgabenteilEntity, { observe: 'response' });
  }

  update(aufgabenteilEntity: IAufgabenteilEntity): Observable<EntityResponseType> {
    return this.http.put<IAufgabenteilEntity>(this.resourceUrl, aufgabenteilEntity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAufgabenteilEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAufgabenteilEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAufgabenteilEntity[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
