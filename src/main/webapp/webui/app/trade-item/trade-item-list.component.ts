import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ROUTE_URLS} from '../app.routes';

import {MessangerService} from '../common/messenger.service';

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
  isLoading: boolean = true;
  tradeItems: TradeItem[];
  selectedTradeItem: TradeItem;
  
  constructor(private router: Router, private tradeItemService: TradeItemService, private messengerService: MessangerService) { }

  private addTradeItem() {
    let link = [ROUTE_URLS.TRADE_ITEM_NEW];
    this.router.navigate(link);
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
    let link = ['/trade-item/trade-item-editor', tradeItem.tradeItemId ];
    this.router.navigate(link);
  }

}
