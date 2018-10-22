import { Moment } from 'moment';

export interface ITask {
    id?: number;
    name?: string;
    dateFrom?: Moment;
    dateTo?: Moment;
    vesrion?: number;
    status?: string;
    goalId?: number;
}

export class Task implements ITask {
    constructor(
        public id?: number,
        public name?: string,
        public dateFrom?: Moment,
        public dateTo?: Moment,
        public vesrion?: number,
        public status?: string,
        public goalId?: number
    ) {}
}
