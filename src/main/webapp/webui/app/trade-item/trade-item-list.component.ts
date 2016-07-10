import { Component, OnInit } from '@angular/core';
import {TradeItem} from './trade-item';
import {TradeItemService} from './trade-item.service';

@Component({
  selector: 'trade-item-list',
  providers: [TradeItemService],
  templateUrl: 'app/trade-item/trade-item-list.html'
})
export class TradeItemListComponent implements OnInit {
  constructor(private tradeItemService: TradeItemService) { }

  tradeItems: TradeItem[];

  ngOnInit() {
    let searchResult = this.tradeItemService.search();
    searchResult
      .then(data => this.tradeItems = data)
      .catch(error => console.log(error));
  }

}