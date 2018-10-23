import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { GoalTaskComponent } from 'app/control/activiti/goal-task/goal-task.component';

@Injectable({ providedIn: 'root' })
export class GoalTaskResolve {}

export const goalTaskRoute: Routes = [
    {
        path: 'goalTask',
        component: GoalTaskComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'officeRestApp.goalTask.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
