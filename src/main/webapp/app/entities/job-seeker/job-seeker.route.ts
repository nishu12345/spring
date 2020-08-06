import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJobSeeker, JobSeeker } from 'app/shared/model/job-seeker.model';
import { JobSeekerService } from './job-seeker.service';
import { JobSeekerComponent } from './job-seeker.component';
import { JobSeekerDetailComponent } from './job-seeker-detail.component';
import { JobSeekerUpdateComponent } from './job-seeker-update.component';

@Injectable({ providedIn: 'root' })
export class JobSeekerResolve implements Resolve<IJobSeeker> {
  constructor(private service: JobSeekerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobSeeker> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jobSeeker: HttpResponse<JobSeeker>) => {
          if (jobSeeker.body) {
            return of(jobSeeker.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobSeeker());
  }
}

export const jobSeekerRoute: Routes = [
  {
    path: '',
    component: JobSeekerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JobSeekers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobSeekerDetailComponent,
    resolve: {
      jobSeeker: JobSeekerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JobSeekers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobSeekerUpdateComponent,
    resolve: {
      jobSeeker: JobSeekerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JobSeekers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobSeekerUpdateComponent,
    resolve: {
      jobSeeker: JobSeekerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JobSeekers',
    },
    canActivate: [UserRouteAccessService],
  },
];
