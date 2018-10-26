import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManagmentComponent } from './managment/managment.component';
import { RouterModule } from '@angular/router';
import { managmentRoute } from 'app/control/managment/managment.route';
import { JhMaterialModule } from 'app/shared/jh-material/jh-material.module';

@NgModule({
    imports: [CommonModule, RouterModule.forChild(managmentRoute), JhMaterialModule],
    exports: [JhMaterialModule],
    declarations: [ManagmentComponent]
})
export class OfficeRestControlModule {}
