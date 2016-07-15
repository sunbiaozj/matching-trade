import { Component, OnInit, ViewChild } from '@angular/core';

import { NavBarAppComponent } from './common/nav-bar-app.component';

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
	directives: [TradeItemListComponent, NavBarAppComponent, ErrorAppComponent],
	template: `
	<nav-bar-app></nav-bar-app>
	<error-app></error-app>
	<trade-item-list></trade-item-list>
	`
})
export class AppComponent {

}