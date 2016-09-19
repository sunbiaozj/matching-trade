import {Component, OnInit} from '@angular/core';

import {AuthenticationService} from '../authentication/authentication.service';
import {MessengerService} from '../common/messenger/messenger.service';

@Component({
	selector: 'home',
	providers: [AuthenticationService],
	templateUrl: 'app/home/home.html'
})
export class HomeComponent implements OnInit {
	private isAuthenticated: boolean = false;

	constructor(
		private authenticationService: AuthenticationService,
		private messengerService: MessengerService) { }

	private authenticate() {
		// Use same as in AuthenticationService.authenticate()
		AuthenticationService.authenticate();
	}

	ngOnInit() {
		this.authenticationService.getLastAuthentication().then(
			authentication => this.isAuthenticated = authentication.isAuthenticated
		).catch(
			error => this.messengerService.setError(error)
		)
	}

}
