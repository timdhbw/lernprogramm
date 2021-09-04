import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AufgabeEntityService } from 'app/entities/aufgabe-entity/aufgabe-entity.service';
import { IAufgabeEntity, AufgabeEntity } from 'app/shared/model/aufgabe-entity.model';
import { KategorieEnum } from 'app/shared/model/enumerations/kategorie-enum.model';

describe('Service Tests', () => {
  describe('AufgabeEntity Service', () => {
    let injector: TestBed;
    let service: AufgabeEntityService;
    let httpMock: HttpTestingController;
    let elemDefault: IAufgabeEntity;
    let expectedResult: IAufgabeEntity | IAufgabeEntity[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AufgabeEntityService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AufgabeEntity(0, 'AAAAAAA', KategorieEnum.SOFTWAREENTWICKLUNG);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AufgabeEntity', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AufgabeEntity()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AufgabeEntity', () => {
        const returnedFromService = Object.assign(
          {
            aufgabentitel: 'BBBBBB',
            kategorie: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AufgabeEntity', () => {
        const returnedFromService = Object.assign(
          {
            aufgabentitel: 'BBBBBB',
            kategorie: 'BBBBBB',
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

      it('should delete a AufgabeEntity', () => {
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
