import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { MyMeritUser } from './my-merit-user.model';
import { MyMeritUserPopupService } from './my-merit-user-popup.service';
import { MyMeritUserService } from './my-merit-user.service';

@Component({
    selector: 'jhi-my-merit-user-delete-dialog',
    templateUrl: './my-merit-user-delete-dialog.component.html'
})
export class MyMeritUserDeleteDialogComponent {

    myMeritUser: MyMeritUser;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private myMeritUserService: MyMeritUserService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['myMeritUser']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.myMeritUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'myMeritUserListModification',
                content: 'Deleted an myMeritUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-my-merit-user-delete-popup',
    template: ''
})
export class MyMeritUserDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private myMeritUserPopupService: MyMeritUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.myMeritUserPopupService
                .open(MyMeritUserDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
