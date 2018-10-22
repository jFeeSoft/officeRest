import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IGoal } from 'app/shared/model/goal.model';
import { GoalService } from './goal.service';

@Component({
    selector: 'jhi-goal-update',
    templateUrl: './goal-update.component.html'
})
export class GoalUpdateComponent implements OnInit {
    goal: IGoal;
    isSaving: boolean;
    dateFromDp: any;
    dateToDp: any;

    constructor(private goalService: GoalService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ goal }) => {
            this.goal = goal;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.goal.id !== undefined) {
            this.subscribeToSaveResponse(this.goalService.update(this.goal));
        } else {
            this.subscribeToSaveResponse(this.goalService.create(this.goal));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGoal>>) {
        result.subscribe((res: HttpResponse<IGoal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
