import {TradeItem} from './trade-item';

export class TradeItemTransformer {

    dataToJson(data:any):TradeItem[] {
        
        let result:TradeItem[] = [ {id:5, name:'myName'}];
        return result;
    }

}
