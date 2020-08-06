import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IJobSeeker, JobSeeker } from 'app/shared/model/job-seeker.model';
import { JobSeekerService } from './job-seeker.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-job-seeker-update',
  templateUrl: './job-seeker-update.component.html',
})
export class JobSeekerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    age: [],
    gender: [],
    experience: [],
    ctc: [],
    expectedCTC: [],
    photo: [],
    photoContentType: [],
    resume: [],
    resumeContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected jobSeekerService: JobSeekerService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobSeeker }) => {
      this.updateForm(jobSeeker);
    });
  }

  updateForm(jobSeeker: IJobSeeker): void {
    this.editForm.patchValue({
      id: jobSeeker.id,
      name: jobSeeker.name,
      age: jobSeeker.age,
      gender: jobSeeker.gender,
      experience: jobSeeker.experience,
      ctc: jobSeeker.ctc,
      expectedCTC: jobSeeker.expectedCTC,
      photo: jobSeeker.photo,
      photoContentType: jobSeeker.photoContentType,
      resume: jobSeeker.resume,
      resumeContentType: jobSeeker.resumeContentType,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('jhipsterCrudApp.error', { message: err.message })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobSeeker = this.createFromForm();
    if (jobSeeker.id !== undefined) {
      this.subscribeToSaveResponse(this.jobSeekerService.update(jobSeeker));
    } else {
      this.subscribeToSaveResponse(this.jobSeekerService.create(jobSeeker));
    }
  }

  private createFromForm(): IJobSeeker {
    return {
      ...new JobSeeker(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      age: this.editForm.get(['age'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      experience: this.editForm.get(['experience'])!.value,
      ctc: this.editForm.get(['ctc'])!.value,
      expectedCTC: this.editForm.get(['expectedCTC'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      resumeContentType: this.editForm.get(['resumeContentType'])!.value,
      resume: this.editForm.get(['resume'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobSeeker>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
