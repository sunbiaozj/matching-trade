import { Component, OnInit } from '@angular/core';
import {TradeItem} from './trade-item';
import {TradeItemService} from './trade-item.service';

@Component({
  selector: 'trade-item-list',
  providers: [TradeItemService],
  template: `
  <h1>Trade Item List</h1>
  <ul>
    <li *ngFor="let tradeItem of tradeItems">{{tradeItem.id}}-{{tradeItem.name}}</li>
  </ul>
  `
})
export class TradeItemListComponent implements OnInit {
  constructor(private tradeItemService: TradeItemService) { }

  tradeItems: TradeItem[];

  ngOnInit() {
    this.tradeItems = this.tradeItemService.search();
  }

}