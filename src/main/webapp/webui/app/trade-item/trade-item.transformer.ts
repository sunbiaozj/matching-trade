import {TradeItem} from './trade-item';

export class TradeItemTransformer {

    dataArrayToJson(data:any[]):TradeItem[] {
        let result:Array<TradeItem> = new Array<TradeItem>();
        data.forEach(e => {
            let tradeItem: TradeItem = this.dataObjetToJson(e);
            result.push(e);
        });
        return result;
    }

    dataObjetToJson(data:any):TradeItem {
        let result:TradeItem = new TradeItem();
        result.tradeItemId = data.id;
        result.name = data.name;
        return result;
    }

}
