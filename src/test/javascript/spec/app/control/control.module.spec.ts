import { ControlModule } from '../../../../../main/webapp/app/control/control.module';

describe('ControlModule', () => {
    let controlModule: ControlModule;

    beforeEach(() => {
        controlModule = new ControlModule();
    });

    it('should create an instance', () => {
        expect(controlModule).toBeTruthy();
    });
});
