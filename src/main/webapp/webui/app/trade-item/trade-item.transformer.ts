import {TradeItem} from './trade-item';

export class TradeItemTransformer {
    public dataArrayToJson(data:any[]):TradeItem[] {
        let result:Array<TradeItem> = new Array<TradeItem>();
        data.forEach(e => {
            let tradeItem: TradeItem = this.dataObjetToJson(e);
            result.push(e);
        });
        return result;
    }

    public dataObjetToJson(data:any):TradeItem {
        let result:TradeItem = new TradeItem();
        result.tradeItemId = data.tradeItemId;
        result.name = data.name;
        result.description = data.description;
        return result;
    }

}
