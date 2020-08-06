import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobSeeker } from 'app/shared/model/job-seeker.model';
import { JobSeekerService } from './job-seeker.service';

@Component({
  templateUrl: './job-seeker-delete-dialog.component.html',
})
export class JobSeekerDeleteDialogComponent {
  jobSeeker?: IJobSeeker;

  constructor(protected jobSeekerService: JobSeekerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.jobSeekerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jobSeekerListModification');
      this.activeModal.close();
    });
  }
}
