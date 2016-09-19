import {Injectable} from '@angular/core';
import {HttpService} from '../common/http/http.service';
import {ResponseResult} from '../common/http/response-result';
import {TradeItem} from './trade-item';
import {TradeItemTransformer} from './trade-item.transformer';

@Injectable()
export class TradeItemService {
    private url: string = 'tradeitems';
    private transformer = new TradeItemTransformer();

    constructor(private httpService: HttpService) { }

    public get(tradeItemId: number): Promise<TradeItem> {
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

    public search(page?: number, limit?: number): Promise<ResponseResult<TradeItem[]>> {
        let parameters = "";
        if (page && limit) {
            parameters = `?_page=${page}&_limit=${limit}`;
        }
        return this.httpService.get(`${this.url}${parameters}`)
            .then(response => {
                let result = new ResponseResult<TradeItem[]>();
                result.pagination = ResponseResult.transformPagination(response.json()._pagination);
                result.data = this.transformer.dataArrayToJson(response.json().data);
                return result;
            });
    }
}
