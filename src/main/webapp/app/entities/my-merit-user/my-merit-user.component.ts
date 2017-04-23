import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { MyMeritUser } from './my-merit-user.model';
import { MyMeritUserService } from './my-merit-user.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-my-merit-user',
    templateUrl: './my-merit-user.component.html'
})
export class MyMeritUserComponent implements OnInit, OnDestroy {
myMeritUsers: MyMeritUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private myMeritUserService: MyMeritUserService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['myMeritUser']);
    }

    loadAll() {
        this.myMeritUserService.query().subscribe(
            (res: Response) => {
                this.myMeritUsers = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMyMeritUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MyMeritUser) {
        return item.id;
    }
    registerChangeInMyMeritUsers() {
        this.eventSubscriber = this.eventManager.subscribe('myMeritUserListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
