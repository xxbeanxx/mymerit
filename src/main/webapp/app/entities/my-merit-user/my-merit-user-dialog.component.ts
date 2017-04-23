import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { MyMeritUser } from './my-merit-user.model';
import { MyMeritUserPopupService } from './my-merit-user-popup.service';
import { MyMeritUserService } from './my-merit-user.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-my-merit-user-dialog',
    templateUrl: './my-merit-user-dialog.component.html'
})
export class MyMeritUserDialogComponent implements OnInit {

    myMeritUser: MyMeritUser;
    authorities: any[];
    isSaving: boolean;

    users: User[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private myMeritUserService: MyMeritUserService,
        private userService: UserService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['myMeritUser']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.myMeritUser.id !== undefined) {
            this.myMeritUserService.update(this.myMeritUser)
                .subscribe((res: MyMeritUser) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.myMeritUserService.create(this.myMeritUser)
                .subscribe((res: MyMeritUser) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: MyMeritUser) {
        this.eventManager.broadcast({ name: 'myMeritUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-my-merit-user-popup',
    template: ''
})
export class MyMeritUserPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private myMeritUserPopupService: MyMeritUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.myMeritUserPopupService
                    .open(MyMeritUserDialogComponent, params['id']);
            } else {
                this.modalRef = this.myMeritUserPopupService
                    .open(MyMeritUserDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
