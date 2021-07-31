import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AufgabenteilEntityService } from 'app/entities/aufgabenteil-entity/aufgabenteil-entity.service';
import { IAufgabenteilEntity, AufgabenteilEntity } from 'app/shared/model/aufgabenteil-entity.model';
import { AufgabenteiltypEnum } from 'app/shared/model/enumerations/aufgabenteiltyp-enum.model';

describe('Service Tests', () => {
  describe('AufgabenteilEntity Service', () => {
    let injector: TestBed;
    let service: AufgabenteilEntityService;
    let httpMock: HttpTestingController;
    let elemDefault: IAufgabenteilEntity;
    let expectedResult: IAufgabenteilEntity | IAufgabenteilEntity[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AufgabenteilEntityService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AufgabenteilEntity(0, 0, AufgabenteiltypEnum.TEXT, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AufgabenteilEntity', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AufgabenteilEntity()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AufgabenteilEntity', () => {
        const returnedFromService = Object.assign(
          {
            laufenNr: 1,
            aufgabenteiltyp: 'BBBBBB',
            text: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AufgabenteilEntity', () => {
        const returnedFromService = Object.assign(
          {
            laufenNr: 1,
            aufgabenteiltyp: 'BBBBBB',
            text: 'BBBBBB',
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

      it('should delete a AufgabenteilEntity', () => {
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
