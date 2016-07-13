import { Component, OnInit, ViewChild } from '@angular/core';

import { ErrorAppComponent } from './common/error-app.component';
import { ErrorAppService } from './common/error-app-service';
import { HttpService } from './common/http.service';

import {TradeItemListComponent} from './trade-item/trade-item-list.component';

// TODO: Move HTTP related imports from MatchingTradeApp to HttpService class, need to inject HTTP_PROVIDERS 
import { Headers, Http } from '@angular/http';
import { HTTP_PROVIDERS } from '@angular/http';
import 'rxjs/add/operator/toPromise';
@Component({
	selector: 'matching-trade-app',
	providers: [HTTP_PROVIDERS, HttpService, ErrorAppService],
	directives: [TradeItemListComponent, ErrorAppComponent],
	template: `
	<error-app></error-app>
	<h1>Matching Trade</h1>
	<trade-item-list></trade-item-list>
	`
})
export class AppComponent {

}