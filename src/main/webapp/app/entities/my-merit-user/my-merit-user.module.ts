import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MymeritSharedModule } from '../../shared';
import { MymeritAdminModule } from '../../admin/admin.module';
import {
    MyMeritUserService,
    MyMeritUserPopupService,
    MyMeritUserComponent,
    MyMeritUserDetailComponent,
    MyMeritUserDialogComponent,
    MyMeritUserPopupComponent,
    MyMeritUserDeletePopupComponent,
    MyMeritUserDeleteDialogComponent,
    myMeritUserRoute,
    myMeritUserPopupRoute,
} from './';

const ENTITY_STATES = [
    ...myMeritUserRoute,
    ...myMeritUserPopupRoute,
];

@NgModule({
    imports: [
        MymeritSharedModule,
        MymeritAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MyMeritUserComponent,
        MyMeritUserDetailComponent,
        MyMeritUserDialogComponent,
        MyMeritUserDeleteDialogComponent,
        MyMeritUserPopupComponent,
        MyMeritUserDeletePopupComponent,
    ],
    entryComponents: [
        MyMeritUserComponent,
        MyMeritUserDialogComponent,
        MyMeritUserPopupComponent,
        MyMeritUserDeleteDialogComponent,
        MyMeritUserDeletePopupComponent,
    ],
    providers: [
        MyMeritUserService,
        MyMeritUserPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MymeritMyMeritUserModule {}
