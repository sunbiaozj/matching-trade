import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ROUTE_URLS } from '../app.routes';

@Component({
	selector: 'nav-bar-app',
	templateUrl: 'app/common/nav-bar-app.html'
})
export class NavBarAppComponent {

	constructor(private router: Router) {}

	private navigate(s:string) {
		if (s == "trade-items") {
			let link = [ROUTE_URLS.TRADE_ITEM_LIST];
			this.router.navigate(link);
		}
	}

}
