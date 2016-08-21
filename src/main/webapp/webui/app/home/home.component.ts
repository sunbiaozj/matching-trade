import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {ROUTE_URLS} from '../app.routes';

import {AuthenticationComponent} from '../authentication/authentication.component';
import {AuthenticationService} from '../authentication/authentication.service';

@Component({
	selector: 'home',
	templateUrl: 'app/home/home.html',
	providers: [AuthenticationService]
})
export class HomeComponent {
	private isAuthenticated: boolean = false;

	constructor(private router: Router, private authenticationService: AuthenticationService) { }

	ngOnInit() {
    	let authenticationPromise = this.authenticationService.get();
    	authenticationPromise.then(authentication => {
			this.isAuthenticated = authentication.isAuthenticated
		});
	}

	private authenticate() {
		// Create AuthenticationComponent object without dependency injection
		let authenticationComponent: AuthenticationComponent = new AuthenticationComponent(null, null, null, null);
		// Use same AuthenticationComponent.authenticate()
		authenticationComponent.authenticate();
	}

}
