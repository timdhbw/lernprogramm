import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MultipleChoiceAntwortEntityService } from 'app/entities/multiple-choice-antwort-entity/multiple-choice-antwort-entity.service';
import { IMultipleChoiceAntwortEntity, MultipleChoiceAntwortEntity } from 'app/shared/model/multiple-choice-antwort-entity.model';

describe('Service Tests', () => {
  describe('MultipleChoiceAntwortEntity Service', () => {
    let injector: TestBed;
    let service: MultipleChoiceAntwortEntityService;
    let httpMock: HttpTestingController;
    let elemDefault: IMultipleChoiceAntwortEntity;
    let expectedResult: IMultipleChoiceAntwortEntity | IMultipleChoiceAntwortEntity[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MultipleChoiceAntwortEntityService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new MultipleChoiceAntwortEntity(0, false, false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MultipleChoiceAntwortEntity', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new MultipleChoiceAntwortEntity()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MultipleChoiceAntwortEntity', () => {
        const returnedFromService = Object.assign(
          {
            checked: true,
            checkedRichtig: true,
            label: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MultipleChoiceAntwortEntity', () => {
        const returnedFromService = Object.assign(
          {
            checked: true,
            checkedRichtig: true,
            label: 'BBBBBB',
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

      it('should delete a MultipleChoiceAntwortEntity', () => {
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
