/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { GoalService } from 'app/entities/goal/goal.service';
import { IGoal, Goal, Status } from 'app/shared/model/goal.model';

describe('Service Tests', () => {
    describe('Goal Service', () => {
        let injector: TestBed;
        let service: GoalService;
        let httpMock: HttpTestingController;
        let elemDefault: IGoal;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(GoalService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Goal(0, 'AAAAAAA', currentDate, currentDate, 0, Status.NEW);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateFrom: currentDate.format(DATE_FORMAT),
                        dateTo: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Goal', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateFrom: currentDate.format(DATE_FORMAT),
                        dateTo: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateFrom: currentDate,
                        dateTo: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Goal(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Goal', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        dateFrom: currentDate.format(DATE_FORMAT),
                        dateTo: currentDate.format(DATE_FORMAT),
                        version: 1,
                        status: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateFrom: currentDate,
                        dateTo: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Goal', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        dateFrom: currentDate.format(DATE_FORMAT),
                        dateTo: currentDate.format(DATE_FORMAT),
                        version: 1,
                        status: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateFrom: currentDate,
                        dateTo: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Goal', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
