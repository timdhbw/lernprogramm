import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IProduktEntity } from 'app/shared/model/produkt-entity.model';

type EntityResponseType = HttpResponse<IProduktEntity>;
type EntityArrayResponseType = HttpResponse<IProduktEntity[]>;

@Injectable({ providedIn: 'root' })
export class ProduktEntityService {
  public resourceUrl = SERVER_API_URL + 'api/produkt-entities';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/produkt-entities';

  constructor(protected http: HttpClient) {}

  create(produktEntity: IProduktEntity): Observable<EntityResponseType> {
    return this.http.post<IProduktEntity>(this.resourceUrl, produktEntity, { observe: 'response' });
  }

  update(produktEntity: IProduktEntity): Observable<EntityResponseType> {
    return this.http.put<IProduktEntity>(this.resourceUrl, produktEntity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProduktEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProduktEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProduktEntity[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
