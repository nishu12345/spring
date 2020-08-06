import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { JobSeekerService } from 'app/entities/job-seeker/job-seeker.service';
import { IJobSeeker, JobSeeker } from 'app/shared/model/job-seeker.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

describe('Service Tests', () => {
  describe('JobSeeker Service', () => {
    let injector: TestBed;
    let service: JobSeekerService;
    let httpMock: HttpTestingController;
    let elemDefault: IJobSeeker;
    let expectedResult: IJobSeeker | IJobSeeker[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(JobSeekerService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new JobSeeker('ID', 'AAAAAAA', 0, Gender.MALE, 0, 0, 0, 'image/png', 'AAAAAAA', 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a JobSeeker', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new JobSeeker()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a JobSeeker', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            age: 1,
            gender: 'BBBBBB',
            experience: 1,
            ctc: 1,
            expectedCTC: 1,
            photo: 'BBBBBB',
            resume: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of JobSeeker', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            age: 1,
            gender: 'BBBBBB',
            experience: 1,
            ctc: 1,
            expectedCTC: 1,
            photo: 'BBBBBB',
            resume: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a JobSeeker', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
