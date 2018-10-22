import { Moment } from 'moment';
import { ITask } from 'app/shared/model//task.model';

export interface IGoal {
    id?: number;
    name?: string;
    dateFrom?: Moment;
    dateTo?: Moment;
    version?: number;
    status?: string;
    tasks?: ITask[];
}

export class Goal implements IGoal {
    constructor(
        public id?: number,
        public name?: string,
        public dateFrom?: Moment,
        public dateTo?: Moment,
        public version?: number,
        public status?: string,
        public tasks?: ITask[]
    ) {}
}
