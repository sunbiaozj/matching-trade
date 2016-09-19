import {Component, OnInit} from '@angular/core';

import {MessengerService} from '../common/messenger/messenger.service';
import {RouterService} from '../common/router/router.service';
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
  private isLoading: boolean = true;
  private tradeItems: TradeItem[];
  private selectedTradeItem: TradeItem;
  
  constructor(
    private routerService: RouterService,
    private tradeItemService: TradeItemService, private messengerService: MessengerService) { }

  private addTradeItem() {

    this.routerService.navigate([this.routerService.routes.TRADE_ITEM_NEW]);
  }

  ngOnInit() {
    let searchResult = this.tradeItemService.search();
    searchResult
      .then(data => {
        this.tradeItems = data;
        this.isLoading = false;
      })
      .catch(error => this.messengerService.setError(error) );
  }

  private navigateToTradeItem(tradeItem: TradeItem) {
    this.selectedTradeItem = tradeItem;
    let link = [this.routerService.routes.TRADE_ITEM_EDIT, tradeItem.tradeItemId];
    this.routerService.navigate(link);
  }

}
