import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MyMeritUserComponent } from './my-merit-user.component';
import { MyMeritUserDetailComponent } from './my-merit-user-detail.component';
import { MyMeritUserPopupComponent } from './my-merit-user-dialog.component';
import { MyMeritUserDeletePopupComponent } from './my-merit-user-delete-dialog.component';

import { Principal } from '../../shared';

export const myMeritUserRoute: Routes = [
  {
    path: 'my-merit-user',
    component: MyMeritUserComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'mymeritApp.myMeritUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'my-merit-user/:id',
    component: MyMeritUserDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'mymeritApp.myMeritUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const myMeritUserPopupRoute: Routes = [
  {
    path: 'my-merit-user-new',
    component: MyMeritUserPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'mymeritApp.myMeritUser.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'my-merit-user/:id/edit',
    component: MyMeritUserPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'mymeritApp.myMeritUser.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'my-merit-user/:id/delete',
    component: MyMeritUserDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'mymeritApp.myMeritUser.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
