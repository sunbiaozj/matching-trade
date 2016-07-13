import { Injectable } from '@angular/core';
import { HttpService } from '../common/http.service';
import { TradeItem } from './trade-item';
import { TradeItemTransformer } from './trade-item.transformer';

@Injectable()
export class TradeItemService {
    url: string = 'tradeitems';
    transformer = new TradeItemTransformer();

    constructor(private httpService: HttpService) { }

    search(): Promise<TradeItem[]> {
        console.log("serarc");
        return this.httpService.get(this.url)
            .then(response => this.transformer.dataArrayToJson(response.json().data));
    }

    save(tradeItem: TradeItem) {
        return this.httpService.post(this.url, tradeItem)
            .then(response => this.transformer.dataObjetToJson(response.json().data));
    }

}
