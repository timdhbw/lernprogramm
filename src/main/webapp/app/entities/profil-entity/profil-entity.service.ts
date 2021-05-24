import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IProfilEntity } from 'app/shared/model/profil-entity.model';

type EntityResponseType = HttpResponse<IProfilEntity>;
type EntityArrayResponseType = HttpResponse<IProfilEntity[]>;

@Injectable({ providedIn: 'root' })
export class ProfilEntityService {
  public resourceUrl = SERVER_API_URL + 'api/profil-entities';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/profil-entities';

  constructor(protected http: HttpClient) {}

  create(profilEntity: IProfilEntity): Observable<EntityResponseType> {
    return this.http.post<IProfilEntity>(this.resourceUrl, profilEntity, { observe: 'response' });
  }

  update(profilEntity: IProfilEntity): Observable<EntityResponseType> {
    return this.http.put<IProfilEntity>(this.resourceUrl, profilEntity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfilEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfilEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfilEntity[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
