import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ROUTE_URLS } from '../app.routes';

import {AuthenticationService} from '../authentication/authentication.service';

@Component({
	selector: 'navigation-bar',
	templateUrl: 'app/navigation-bar/navigation-bar.html',
	providers: [AuthenticationService]
})
export class NavBarAppComponent implements OnInit {
	private authenticatedEmail: string;

	constructor(private router: Router, private authenticationService: AuthenticationService) { }

	ngOnInit() {
    	let authenticationPromise = this.authenticationService.get();
    	authenticationPromise.then(authentication => this.authenticatedEmail = authentication.email);
	}

	private navigate(s: string) {
		let link: any;
		switch (s) {
			case "trade-items":
				link = [ROUTE_URLS.TRADE_ITEM_LIST];
				break;
			case "authentication":
				link = [ROUTE_URLS.AUTHENTICATION];
				break;
			default:
				// Does nothing
				break;
		}
		this.router.navigate(link);
	}

}
