import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAufgabeEntity } from 'app/shared/model/aufgabe-entity.model';

type EntityResponseType = HttpResponse<IAufgabeEntity>;
type EntityArrayResponseType = HttpResponse<IAufgabeEntity[]>;

@Injectable({ providedIn: 'root' })
export class AufgabeEntityService {
  public resourceUrl = SERVER_API_URL + 'api/aufgabe-entities';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/aufgabe-entities';

  constructor(protected http: HttpClient) {}

  create(aufgabeEntity: IAufgabeEntity): Observable<EntityResponseType> {
    return this.http.post<IAufgabeEntity>(this.resourceUrl, aufgabeEntity, { observe: 'response' });
  }

  update(aufgabeEntity: IAufgabeEntity): Observable<EntityResponseType> {
    return this.http.put<IAufgabeEntity>(this.resourceUrl, aufgabeEntity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAufgabeEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAufgabeEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAufgabeEntity[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
