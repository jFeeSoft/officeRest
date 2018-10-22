import { Moment } from 'moment';

export const enum Status {
    CREATED = 'CREATED',
    NEW = 'NEW',
    PAUSED = 'PAUSED',
    IN_PROGRESS = 'IN_PROGRESS',
    DONE = 'DONE'
}

export interface ITask {
    id?: number;
    name?: string;
    dateFrom?: Moment;
    dateTo?: Moment;
    version?: number;
    status?: Status;
    goalId?: number;
}

export class Task implements ITask {
    constructor(
        public id?: number,
        public name?: string,
        public dateFrom?: Moment,
        public dateTo?: Moment,
        public version?: number,
        public status?: Status,
        public goalId?: number
    ) {}
}
