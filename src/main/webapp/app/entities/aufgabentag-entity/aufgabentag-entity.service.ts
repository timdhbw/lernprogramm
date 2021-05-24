import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';

type EntityResponseType = HttpResponse<IAufgabentagEntity>;
type EntityArrayResponseType = HttpResponse<IAufgabentagEntity[]>;

@Injectable({ providedIn: 'root' })
export class AufgabentagEntityService {
  public resourceUrl = SERVER_API_URL + 'api/aufgabentag-entities';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/aufgabentag-entities';

  constructor(protected http: HttpClient) {}

  create(aufgabentagEntity: IAufgabentagEntity): Observable<EntityResponseType> {
    return this.http.post<IAufgabentagEntity>(this.resourceUrl, aufgabentagEntity, { observe: 'response' });
  }

  update(aufgabentagEntity: IAufgabentagEntity): Observable<EntityResponseType> {
    return this.http.put<IAufgabentagEntity>(this.resourceUrl, aufgabentagEntity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAufgabentagEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAufgabentagEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAufgabentagEntity[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
