import { Component, OnInit } from '@angular/core';
import {TradeItemListComponent} from './trade-item/trade-item-list.component';

@Component({
	selector: 'matching-trade-app',
	directives: [TradeItemListComponent],
	template: `
	<h1>Matching Trade</h1>
	<trade-item-list></trade-item-list>
	`
})
export class MatchingTradeAppComponent implements OnInit {
	ngOnInit() {
	}
}