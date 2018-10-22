import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoal } from 'app/shared/model/goal.model';

@Component({
    selector: 'jhi-goal-detail',
    templateUrl: './goal-detail.component.html'
})
export class GoalDetailComponent implements OnInit {
    goal: IGoal;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ goal }) => {
            this.goal = goal;
        });
    }

    previousState() {
        window.history.back();
    }
}
