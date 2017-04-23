import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MymeritTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MyMeritUserDetailComponent } from '../../../../../../main/webapp/app/entities/my-merit-user/my-merit-user-detail.component';
import { MyMeritUserService } from '../../../../../../main/webapp/app/entities/my-merit-user/my-merit-user.service';
import { MyMeritUser } from '../../../../../../main/webapp/app/entities/my-merit-user/my-merit-user.model';

describe('Component Tests', () => {

    describe('MyMeritUser Management Detail Component', () => {
        let comp: MyMeritUserDetailComponent;
        let fixture: ComponentFixture<MyMeritUserDetailComponent>;
        let service: MyMeritUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MymeritTestModule],
                declarations: [MyMeritUserDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MyMeritUserService,
                    EventManager
                ]
            }).overrideComponent(MyMeritUserDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MyMeritUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MyMeritUserService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MyMeritUser(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.myMeritUser).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
