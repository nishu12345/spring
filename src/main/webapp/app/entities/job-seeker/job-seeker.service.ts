import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJobSeeker } from 'app/shared/model/job-seeker.model';

type EntityResponseType = HttpResponse<IJobSeeker>;
type EntityArrayResponseType = HttpResponse<IJobSeeker[]>;

@Injectable({ providedIn: 'root' })
export class JobSeekerService {
  public resourceUrl = SERVER_API_URL + 'api/job-seekers';

  constructor(protected http: HttpClient) {}

  create(jobSeeker: IJobSeeker): Observable<EntityResponseType> {
    return this.http.post<IJobSeeker>(this.resourceUrl, jobSeeker, { observe: 'response' });
  }

  update(jobSeeker: IJobSeeker): Observable<EntityResponseType> {
    return this.http.put<IJobSeeker>(this.resourceUrl, jobSeeker, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IJobSeeker>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJobSeeker[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
