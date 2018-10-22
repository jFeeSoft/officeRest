import { Moment } from 'moment';
import { ITask } from 'app/shared/model//task.model';

export const enum Status {
    CREATED = 'CREATED',
    NEW = 'NEW',
    PAUSED = 'PAUSED',
    IN_PROGRESS = 'IN_PROGRESS',
    DONE = 'DONE'
}

export interface IGoal {
    id?: number;
    name?: string;
    dateFrom?: Moment;
    dateTo?: Moment;
    version?: number;
    status?: Status;
    tasks?: ITask[];
}

export class Goal implements IGoal {
    constructor(
        public id?: number,
        public name?: string,
        public dateFrom?: Moment,
        public dateTo?: Moment,
        public version?: number,
        public status?: Status,
        public tasks?: ITask[]
    ) {}
}
