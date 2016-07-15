import { Injectable } from '@angular/core';
import { HttpService } from '../common/http.service';
import { TradeItem } from './trade-item';
import { TradeItemTransformer } from './trade-item.transformer';

@Injectable()
export class TradeItemService {
    private url: string = 'tradeitems';
    private transformer = new TradeItemTransformer();

    constructor(private httpService: HttpService) { }

    public get(tradeItemId: number): Promise<TradeItem> {
        console.log("gettradeitem");
        return this.httpService
            .get(this.url + "/" + tradeItemId)
            .then(response => this.transformer.dataObjetToJson(response.json().data));
    }

    public save(tradeItem: TradeItem): Promise<TradeItem> {
        if (tradeItem.tradeItemId) {
            return this.httpService.put(this.url, tradeItem)
                .then(response => this.transformer.dataObjetToJson(response.json().data));       
        } else {
            return this.httpService.post(this.url, tradeItem)
                .then(response => this.transformer.dataObjetToJson(response.json().data));
        }
    }

    public search(): Promise<TradeItem[]> {
        return this.httpService.get(this.url)
            .then(response => this.transformer.dataArrayToJson(response.json().data));
    }

}
