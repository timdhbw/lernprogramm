import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IMultipleChoiceAntwortEntity } from 'app/shared/model/multiple-choice-antwort-entity.model';

type EntityResponseType = HttpResponse<IMultipleChoiceAntwortEntity>;
type EntityArrayResponseType = HttpResponse<IMultipleChoiceAntwortEntity[]>;

@Injectable({ providedIn: 'root' })
export class MultipleChoiceAntwortEntityService {
  public resourceUrl = SERVER_API_URL + 'api/multiple-choice-antwort-entities';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/multiple-choice-antwort-entities';

  constructor(protected http: HttpClient) {}

  create(multipleChoiceAntwortEntity: IMultipleChoiceAntwortEntity): Observable<EntityResponseType> {
    return this.http.post<IMultipleChoiceAntwortEntity>(this.resourceUrl, multipleChoiceAntwortEntity, { observe: 'response' });
  }

  update(multipleChoiceAntwortEntity: IMultipleChoiceAntwortEntity): Observable<EntityResponseType> {
    return this.http.put<IMultipleChoiceAntwortEntity>(this.resourceUrl, multipleChoiceAntwortEntity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMultipleChoiceAntwortEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMultipleChoiceAntwortEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMultipleChoiceAntwortEntity[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
