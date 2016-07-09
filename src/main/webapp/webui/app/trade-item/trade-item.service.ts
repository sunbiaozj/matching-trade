import { Injectable } from '@angular/core';
import { TradeItem } from './trade-item';

@Injectable()
export class TradeItemService {
    tradeItemsMock: TradeItem[] = [
        { id: 1, name: 'One' },
        { id: 2, name: 'Two' },
        { id: 3, name: 'Three' },
        { id: 4, name: 'Four' },
    ];

    search() {
        return this.tradeItemsMock;
    }
}
