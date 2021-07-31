import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AufgabenhistorieEntityService } from 'app/entities/aufgabenhistorie-entity/aufgabenhistorie-entity.service';
import { IAufgabenhistorieEntity, AufgabenhistorieEntity } from 'app/shared/model/aufgabenhistorie-entity.model';

describe('Service Tests', () => {
  describe('AufgabenhistorieEntity Service', () => {
    let injector: TestBed;
    let service: AufgabenhistorieEntityService;
    let httpMock: HttpTestingController;
    let elemDefault: IAufgabenhistorieEntity;
    let expectedResult: IAufgabenhistorieEntity | IAufgabenhistorieEntity[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AufgabenhistorieEntityService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AufgabenhistorieEntity(0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datum: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AufgabenhistorieEntity', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datum: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datum: currentDate,
          },
          returnedFromService
        );

        service.create(new AufgabenhistorieEntity()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AufgabenhistorieEntity', () => {
        const returnedFromService = Object.assign(
          {
            datum: currentDate.format(DATE_FORMAT),
            bewertungsveraenderung: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datum: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AufgabenhistorieEntity', () => {
        const returnedFromService = Object.assign(
          {
            datum: currentDate.format(DATE_FORMAT),
            bewertungsveraenderung: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datum: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AufgabenhistorieEntity', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

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
