import { ActivitiModule } from '../../../../../../main/webapp/app/control/activiti/activiti.module';

describe('ActivitiModule', () => {
    let activitiModule: ActivitiModule;

    beforeEach(() => {
        activitiModule = new ActivitiModule();
    });

    it('should create an instance', () => {
        expect(activitiModule).toBeTruthy();
    });
});
