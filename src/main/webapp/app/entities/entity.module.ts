import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { OfficeRestGoalModule } from './goal/goal.module';
import { OfficeRestTaskModule } from './task/task.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        OfficeRestGoalModule,
        OfficeRestTaskModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OfficeRestEntityModule {}
