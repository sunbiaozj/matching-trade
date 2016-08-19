import { Component, OnInit, ViewChild } from '@angular/core';

import { ROUTER_DIRECTIVES } from '@angular/router';

import { NavBarAppComponent } from './navigation-bar/navigation-bar.component';
import { ErrorAppComponent } from './common/error-app.component';
import { ErrorAppService } from './common/error-app-service';

import { HttpService } from './common/http.service';

// TODO: Move HTTP related imports from MatchingTradeApp to HttpService class, need to inject HTTP_PROVIDERS 
import { Headers, Http } from '@angular/http';
import { HTTP_PROVIDERS } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Component({
	selector: 'matching-trade-app',
	providers: [HTTP_PROVIDERS, HttpService, ErrorAppService],
	directives: [ROUTER_DIRECTIVES, NavBarAppComponent, ErrorAppComponent],
	template: `
	<navigation-bar></navigation-bar>
	<error-app></error-app>
	<router-outlet></router-outlet>
	`
})
export class AppComponent {

}