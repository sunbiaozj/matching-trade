import { Component, ViewChild } from '@angular/core';

import { ROUTER_DIRECTIVES } from '@angular/router';

import { NavBarAppComponent } from './common/navigation-bar/navigation-bar.component';
import { MessengerComponent } from './common/messenger/messenger.component';
import { MessengerService } from './common/messenger/messenger.service';
import { RouterService } from './common/router/router.service';
import { HttpService } from './common/http/http.service';

import { AuthenticationService } from './authentication/authentication.service';

// TODO: Move HTTP related imports from MatchingTradeApp to HttpService class, need to inject HTTP_PROVIDERS 
import { Headers, Http } from '@angular/http';
import { HTTP_PROVIDERS } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Component({
	selector: 'matching-trade-app',
	providers: [HTTP_PROVIDERS, HttpService, MessengerService, AuthenticationService, RouterService],
	directives: [ROUTER_DIRECTIVES, NavBarAppComponent, MessengerComponent],
	template: `
	<navigation-bar></navigation-bar>
	<messenger></messenger>
	<router-outlet></router-outlet>
	`
})
export class AppComponent {

}