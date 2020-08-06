import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterCrudSharedModule } from 'app/shared/shared.module';
import { JobSeekerComponent } from './job-seeker.component';
import { JobSeekerDetailComponent } from './job-seeker-detail.component';
import { JobSeekerUpdateComponent } from './job-seeker-update.component';
import { JobSeekerDeleteDialogComponent } from './job-seeker-delete-dialog.component';
import { jobSeekerRoute } from './job-seeker.route';

@NgModule({
  imports: [JhipsterCrudSharedModule, RouterModule.forChild(jobSeekerRoute)],
  declarations: [JobSeekerComponent, JobSeekerDetailComponent, JobSeekerUpdateComponent, JobSeekerDeleteDialogComponent],
  entryComponents: [JobSeekerDeleteDialogComponent],
})
export class JhipsterCrudJobSeekerModule {}
