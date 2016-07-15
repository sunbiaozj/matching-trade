import { Component, OnInit } from '@angular/core';

import { ErrorAppService } from '../common/error-app-service';


import {TradeItem} from './trade-item';
import {TradeItemEditorComponent} from './trade-item-editor.component';
import {TradeItemService} from './trade-item.service';

@Component({
  selector: 'trade-item-list',
  providers: [TradeItemService],
  directives: [TradeItemEditorComponent],
  templateUrl: 'app/trade-item/trade-item-list.html'
})
export class TradeItemListComponent implements OnInit {
  tradeItems: TradeItem[];
  selectedTradeItem: TradeItem;
  

  constructor(private tradeItemService: TradeItemService, private errorAppService: ErrorAppService) { }

  ngOnInit() {
    let searchResult = this.tradeItemService.search();
    searchResult
      .then(data => this.tradeItems = data)
      .catch(error => this.errorAppService.addError(error) );
  }

  selectTradeItem(tradeItem: TradeItem) {
    this.selectedTradeItem = tradeItem;
  }

}