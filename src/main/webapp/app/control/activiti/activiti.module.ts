import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GoalTaskComponent } from './goal-task/goal-task.component';
import { goalTaskRoute } from 'app/control/activiti/goal-task/goal-task.route';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [CommonModule, RouterModule.forChild(goalTaskRoute)],
    declarations: [GoalTaskComponent]
})
export class ActivitiModule {}
