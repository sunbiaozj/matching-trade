import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ROUTE_URLS } from '../app.routes';

import {AuthenticationService} from '../authentication/authentication.service';
import {HomeComponent} from '../home/home.component';

@Component({
	selector: 'navigation-bar',
	templateUrl: 'app/navigation-bar/navigation-bar.html',
	providers: [AuthenticationService]
})
export class NavBarAppComponent implements OnInit {
	private isAuthenticated: boolean;

	constructor(private router: Router, private authenticationService: AuthenticationService) { }

	ngOnInit() {
    	let authenticationPromise = this.authenticationService.get();
    	authenticationPromise.then(authentication => this.isAuthenticated = authentication.isAuthenticated);
	}

	private navigate(s: string) {
		let link: any;
		switch (s) {
			case "authentication":
				link = [ROUTE_URLS.AUTHENTICATION];
				break;
			case "trade-items":
				link = [ROUTE_URLS.TRADE_ITEM_LIST];
				break;
			case "home":
				link = [ROUTE_URLS.HOME];
				break;
			default:
				// Does nothing
				break;
		}
		this.router.navigate(link);
	}

}
