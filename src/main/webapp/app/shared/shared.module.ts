import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { HasAnyAuthorityDirective, JhiLoginModalComponent, OfficeRestSharedCommonModule, OfficeRestSharedLibsModule } from './';
import { JhMaterialModule } from 'app/shared/jh-material/jh-material.module';

@NgModule({
    imports: [OfficeRestSharedLibsModule, OfficeRestSharedCommonModule, JhMaterialModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [OfficeRestSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, JhMaterialModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OfficeRestSharedModule {}
