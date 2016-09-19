import {Component, OnInit} from '@angular/core';

import {MessengerService} from '../common/messenger/messenger.service';
import {RouterService} from '../common/router/router.service';
import {Pagination} from '../common/http/response-result';
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
  private selectedTradeItem: TradeItem;
  private pagination: Pagination;
  private tradeItems: TradeItem[];

  constructor(
    private routerService: RouterService,
    private tradeItemService: TradeItemService, private messengerService: MessengerService) { }

  private addTradeItem() {
    this.routerService.navigate([this.routerService.routes.TRADE_ITEM_NEW]);
  }

  private getTradeItems(pageNumber: number) {
    this.isLoading = true;
    let searchResult = this.tradeItemService.search(pageNumber, 10)
      .then(response => {
        this.pagination = response.pagination;
        this.tradeItems = response.data;
        this.isLoading = false;
      })
      .catch(error => this.messengerService.setError(error));
  }

  ngOnInit() {
    this.getTradeItems(1);
  }

  private navigateToTradeItem(tradeItem: TradeItem) {
    this.selectedTradeItem = tradeItem;
    let link = [this.routerService.routes.TRADE_ITEM_EDIT, tradeItem.tradeItemId];
    this.routerService.navigate(link);
  }

}
