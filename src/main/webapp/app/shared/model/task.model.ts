import { Moment } from 'moment';

export const enum Status {
    NEW = 'NEW',
    IN_PROGRESS = 'IN_PROGRESS',
    END = 'END'
}

export interface ITask {
    id?: number;
    name?: string;
    dateFrom?: Moment;
    dateTo?: Moment;
    version?: number;
    status?: Status;
    goalName?: string;
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
        public goalName?: string,
        public goalId?: number
    ) {}
}
