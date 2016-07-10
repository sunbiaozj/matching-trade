import { Injectable } from '@angular/core';
import { TradeItem } from './trade-item';
import { TradeItemTransformer } from './trade-item.transformer';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class TradeItemService {
    url:string = '/matching-trade/services/mt/tradeitems';
    transformer = new TradeItemTransformer();

    tradeItemsMock: TradeItem[] = [
        { id: 1, name: 'One' },
        { id: 2, name: 'Two' },
        { id: 3, name: 'Three' },
        { id: 4, name: 'Four' },
    ];

    constructor(private http: Http) { }    

    search(): Promise<TradeItem[]> {
         return this.http.get(this.url)
               .toPromise()
               .then(response => this.transformer.dataToJson(response.json().data) )
               .catch(error => console.log(error));
    }
}
