import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BewerteterAufgabentagEntityService } from 'app/entities/bewerteter-aufgabentag-entity/bewerteter-aufgabentag-entity.service';
import { IBewerteterAufgabentagEntity, BewerteterAufgabentagEntity } from 'app/shared/model/bewerteter-aufgabentag-entity.model';

describe('Service Tests', () => {
  describe('BewerteterAufgabentagEntity Service', () => {
    let injector: TestBed;
    let service: BewerteterAufgabentagEntityService;
    let httpMock: HttpTestingController;
    let elemDefault: IBewerteterAufgabentagEntity;
    let expectedResult: IBewerteterAufgabentagEntity | IBewerteterAufgabentagEntity[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BewerteterAufgabentagEntityService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new BewerteterAufgabentagEntity(0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BewerteterAufgabentagEntity', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new BewerteterAufgabentagEntity()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BewerteterAufgabentagEntity', () => {
        const returnedFromService = Object.assign(
          {
            bewertung: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BewerteterAufgabentagEntity', () => {
        const returnedFromService = Object.assign(
          {
            bewertung: 1,
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

      it('should delete a BewerteterAufgabentagEntity', () => {
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
