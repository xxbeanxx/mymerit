import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MyMeritUser } from './my-merit-user.model';
import { MyMeritUserService } from './my-merit-user.service';
@Injectable()
export class MyMeritUserPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private myMeritUserService: MyMeritUserService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.myMeritUserService.find(id).subscribe((myMeritUser) => {
                this.myMeritUserModalRef(component, myMeritUser);
            });
        } else {
            return this.myMeritUserModalRef(component, new MyMeritUser());
        }
    }

    myMeritUserModalRef(component: Component, myMeritUser: MyMeritUser): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.myMeritUser = myMeritUser;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
