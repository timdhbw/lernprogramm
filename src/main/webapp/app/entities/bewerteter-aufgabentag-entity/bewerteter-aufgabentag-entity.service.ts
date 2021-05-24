import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IBewerteterAufgabentagEntity } from 'app/shared/model/bewerteter-aufgabentag-entity.model';

type EntityResponseType = HttpResponse<IBewerteterAufgabentagEntity>;
type EntityArrayResponseType = HttpResponse<IBewerteterAufgabentagEntity[]>;

@Injectable({ providedIn: 'root' })
export class BewerteterAufgabentagEntityService {
  public resourceUrl = SERVER_API_URL + 'api/bewerteter-aufgabentag-entities';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/bewerteter-aufgabentag-entities';

  constructor(protected http: HttpClient) {}

  create(bewerteterAufgabentagEntity: IBewerteterAufgabentagEntity): Observable<EntityResponseType> {
    return this.http.post<IBewerteterAufgabentagEntity>(this.resourceUrl, bewerteterAufgabentagEntity, { observe: 'response' });
  }

  update(bewerteterAufgabentagEntity: IBewerteterAufgabentagEntity): Observable<EntityResponseType> {
    return this.http.put<IBewerteterAufgabentagEntity>(this.resourceUrl, bewerteterAufgabentagEntity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBewerteterAufgabentagEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBewerteterAufgabentagEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBewerteterAufgabentagEntity[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
