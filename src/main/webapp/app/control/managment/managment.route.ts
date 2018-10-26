import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { ManagmentComponent } from 'app/control/managment/managment.component';
import { Routes } from '@angular/router';

export const managmentRoute: Routes = [
    {
        path: 'managment',
        component: ManagmentComponent,
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
