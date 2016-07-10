import { Component, OnInit } from '@angular/core';
import { HttpService } from './common/http.service';

import {TradeItemListComponent} from './trade-item/trade-item-list.component';

// TODO: Move HTTP related imports from MatchingTradeApp to HttpService class, need to inject HTTP_PROVIDERS 
import { Headers, Http } from '@angular/http';
import { HTTP_PROVIDERS } from '@angular/http';
import 'rxjs/add/operator/toPromise';
@Component({
	selector: 'matching-trade-app',
	providers: [HTTP_PROVIDERS, HttpService ],
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