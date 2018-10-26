import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { JhMaterialModule } from 'app/shared/jh-material/jh-material.module';
import { ManagementComponent } from 'app/control/management/management.component';
import { managementRoute } from 'app/control/management/management.route';

@NgModule({
    imports: [CommonModule, RouterModule.forChild(managementRoute), JhMaterialModule],
    exports: [JhMaterialModule],
    declarations: [ManagementComponent]
})
export class OfficeRestControlModule {}
