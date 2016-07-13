import { Component } from '@angular/core';

import {TradeItem} from './trade-item';
import {TradeItemService} from './trade-item.service';

@Component({
  selector: 'trade-item-list',
  providers: [TradeItemService],
  templateUrl: 'app/trade-item/trade-item-editor.html'
})
export class TradeItemEditorComponent {
  tradeItem: TradeItem;

  constructor(private tradeItemService: TradeItemService) { }

  save(t: TradeItem):void {
    this.tradeItemService.save(t)
      .then( response => {} )
      .catch( error => {} );
  }
}