import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobSeeker } from 'app/shared/model/job-seeker.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { JobSeekerService } from './job-seeker.service';
import { JobSeekerDeleteDialogComponent } from './job-seeker-delete-dialog.component';

@Component({
  selector: 'jhi-job-seeker',
  templateUrl: './job-seeker.component.html',
})
export class JobSeekerComponent implements OnInit, OnDestroy {
  jobSeekers: IJobSeeker[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected jobSeekerService: JobSeekerService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.jobSeekers = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.jobSeekerService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IJobSeeker[]>) => this.paginateJobSeekers(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.jobSeekers = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJobSeekers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IJobSeeker): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInJobSeekers(): void {
    this.eventSubscriber = this.eventManager.subscribe('jobSeekerListModification', () => this.reset());
  }

  delete(jobSeeker: IJobSeeker): void {
    const modalRef = this.modalService.open(JobSeekerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jobSeeker = jobSeeker;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateJobSeekers(data: IJobSeeker[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.jobSeekers.push(data[i]);
      }
    }
  }
}
