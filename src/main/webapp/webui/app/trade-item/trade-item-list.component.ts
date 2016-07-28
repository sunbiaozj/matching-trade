import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ROUTE_URLS} from '../app.routes';

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
  
  constructor(private router: Router, private tradeItemService: TradeItemService, private errorAppService: ErrorAppService) { }

  private addTradeItem() {
    let link = [ROUTE_URLS.TRADE_ITEM_NEW];
    this.router.navigate(link);
  }

  ngOnInit() {
    let searchResult = this.tradeItemService.search();
    searchResult
      .then(data => this.tradeItems = data)
      .catch(error => this.errorAppService.addError(error) );
  }

  private navigateToTradeItem(tradeItem: TradeItem) {
    this.selectedTradeItem = tradeItem;
    let link = ['/trade-item/trade-item-editor', tradeItem.tradeItemId ];
    this.router.navigate(link);
  }

}
