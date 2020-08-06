import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { JhipsterCrudTestModule } from '../../../test.module';
import { JobSeekerDetailComponent } from 'app/entities/job-seeker/job-seeker-detail.component';
import { JobSeeker } from 'app/shared/model/job-seeker.model';

describe('Component Tests', () => {
  describe('JobSeeker Management Detail Component', () => {
    let comp: JobSeekerDetailComponent;
    let fixture: ComponentFixture<JobSeekerDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ jobSeeker: new JobSeeker('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterCrudTestModule],
        declarations: [JobSeekerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JobSeekerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobSeekerDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load jobSeeker on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobSeeker).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
