import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterCrudTestModule } from '../../../test.module';
import { JobSeekerUpdateComponent } from 'app/entities/job-seeker/job-seeker-update.component';
import { JobSeekerService } from 'app/entities/job-seeker/job-seeker.service';
import { JobSeeker } from 'app/shared/model/job-seeker.model';

describe('Component Tests', () => {
  describe('JobSeeker Management Update Component', () => {
    let comp: JobSeekerUpdateComponent;
    let fixture: ComponentFixture<JobSeekerUpdateComponent>;
    let service: JobSeekerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterCrudTestModule],
        declarations: [JobSeekerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JobSeekerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobSeekerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobSeekerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JobSeeker('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new JobSeeker();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
