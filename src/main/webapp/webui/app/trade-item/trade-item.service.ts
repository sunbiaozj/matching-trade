import { Injectable } from '@angular/core';
import { HttpService } from '../common/http.service';
import { TradeItem } from './trade-item';
import { TradeItemTransformer } from './trade-item.transformer';

@Injectable()
export class TradeItemService {
    url:string = 'tradeitems';
    transformer = new TradeItemTransformer();

    constructor(private httpService: HttpService) { }    

    search(): Promise<TradeItem[]> {
        return this.httpService.get(this.url)
            .then(response => this.transformer.dataArrayToJson(response.json().data) )
            .catch(error => console.log(error));
    }
    
}
