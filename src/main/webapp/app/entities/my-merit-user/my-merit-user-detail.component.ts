import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { MyMeritUser } from './my-merit-user.model';
import { MyMeritUserService } from './my-merit-user.service';

@Component({
    selector: 'jhi-my-merit-user-detail',
    templateUrl: './my-merit-user-detail.component.html'
})
export class MyMeritUserDetailComponent implements OnInit, OnDestroy {

    myMeritUser: MyMeritUser;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private myMeritUserService: MyMeritUserService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['myMeritUser']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMyMeritUsers();
    }

    load(id) {
        this.myMeritUserService.find(id).subscribe((myMeritUser) => {
            this.myMeritUser = myMeritUser;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMyMeritUsers() {
        this.eventSubscriber = this.eventManager.subscribe('myMeritUserListModification', (response) => this.load(this.myMeritUser.id));
    }
}
