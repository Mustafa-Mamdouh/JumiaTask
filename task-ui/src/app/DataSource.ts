import { NumberData } from "./Number";
import { BehaviorSubject, Observable } from 'rxjs';
import { DataSource } from '@angular/cdk/collections';

export class ExampleDataSource extends DataSource<NumberData> {
    data: any = [];
    /** Stream of data that is provided to the table. */
    constructor(ELEMENT_DATA: NumberData[]) {
        super();
        this.data = new BehaviorSubject<NumberData[]>(ELEMENT_DATA);
    }

    /** Connect function called by the table to retrieve one stream containing the data to render. */
    connect(): Observable<NumberData[]> {
        return this.data;
    }

    disconnect() { }
}