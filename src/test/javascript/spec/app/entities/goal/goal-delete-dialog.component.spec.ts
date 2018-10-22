/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OfficeRestTestModule } from '../../../test.module';
import { GoalDeleteDialogComponent } from 'app/entities/goal/goal-delete-dialog.component';
import { GoalService } from 'app/entities/goal/goal.service';

describe('Component Tests', () => {
    describe('Goal Management Delete Component', () => {
        let comp: GoalDeleteDialogComponent;
        let fixture: ComponentFixture<GoalDeleteDialogComponent>;
        let service: GoalService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OfficeRestTestModule],
                declarations: [GoalDeleteDialogComponent]
            })
                .overrideTemplate(GoalDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GoalDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GoalService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
