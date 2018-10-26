import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Routes } from '@angular/router';
import { ManagementComponent } from 'app/control/management/management.component';

export const managementRoute: Routes = [
    {
        path: 'management',
        component: ManagementComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'officeRestApp.task.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
