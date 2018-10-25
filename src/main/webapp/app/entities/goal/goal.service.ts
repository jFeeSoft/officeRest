import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGoal } from 'app/shared/model/goal.model';

type EntityResponseType = HttpResponse<IGoal>;
type EntityArrayResponseType = HttpResponse<IGoal[]>;

@Injectable({ providedIn: 'root' })
export class GoalService {
    public resourceUrl = SERVER_API_URL + 'api/goals';

    constructor(private http: HttpClient) {}

    create(goal: IGoal): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(goal);
        return this.http
            .post<IGoal>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(goal: IGoal): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(goal);
        return this.http
            .put<IGoal>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IGoal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGoal[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(goal: IGoal): IGoal {
        const copy: IGoal = Object.assign({}, goal, {
            dateFrom: goal.dateFrom != null && goal.dateFrom.isValid() ? goal.dateFrom.format(DATE_FORMAT) : null,
            dateTo: goal.dateTo != null && goal.dateTo.isValid() ? goal.dateTo.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateFrom = res.body.dateFrom != null ? moment(res.body.dateFrom) : null;
        res.body.dateTo = res.body.dateTo != null ? moment(res.body.dateTo) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((goal: IGoal) => {
            goal.dateFrom = goal.dateFrom != null ? moment(goal.dateFrom) : null;
            goal.dateTo = goal.dateTo != null ? moment(goal.dateTo) : null;
        });
        return res;
    }
}
