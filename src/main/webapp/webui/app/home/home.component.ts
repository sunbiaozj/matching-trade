import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {ROUTE_URLS} from '../app.routes';

import {AuthenticationComponent} from '../authentication/authentication.component';

@Component({
	selector: 'home',
	templateUrl: 'app/home/home.html'
})
export class HomeComponent {
	private isAuthenticated: boolean;

	constructor(private router: Router) { }

	private authenticate() {
		AuthenticationComponent.authenticate();
	}

}
