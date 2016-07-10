import { Component, OnInit } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { HTTP_PROVIDERS } from '@angular/http';
import 'rxjs/add/operator/toPromise';

import {TradeItemListComponent} from './trade-item/trade-item-list.component';

@Component({
	selector: 'matching-trade-app',
	providers: [HTTP_PROVIDERS],
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