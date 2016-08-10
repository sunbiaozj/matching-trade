import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ROUTE_URLS } from '../app.routes';

@Component({
	selector: 'nav-bar-app',
	templateUrl: 'app/common/nav-bar-app.html'
})
export class NavBarAppComponent {

	constructor(private router: Router) { }

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
