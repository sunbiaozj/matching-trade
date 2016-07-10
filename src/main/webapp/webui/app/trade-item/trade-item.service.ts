import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { TradeItem } from './trade-item';
import { TradeItemTransformer } from './trade-item.transformer';

@Injectable()
export class TradeItemService {
    url:string = '/matching-trade/services/mt/tradeitems';
    transformer = new TradeItemTransformer();

    constructor(private http: Http) { }    

    search(): Promise<TradeItem[]> {
         return this.http.get(this.url)
               .toPromise()
               .then(response => this.transformer.dataArrayToJson(response.json().data) )
               .catch(error => console.log(error));
    }
    
}
