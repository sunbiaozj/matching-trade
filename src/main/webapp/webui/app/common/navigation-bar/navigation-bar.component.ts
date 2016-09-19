import {Component, OnInit} from '@angular/core';

import {AuthenticationService} from '../../authentication/authentication.service';
import {HomeComponent} from '../../home/home.component';

@Component({
	selector: 'navigation-bar',
	templateUrl: 'app/common/navigation-bar/navigation-bar.html'
})
export class NavBarAppComponent implements OnInit {
	private isAuthenticated: boolean;

	constructor(private authenticationService: AuthenticationService) { }

	ngOnInit() {
    	let authenticationPromise = this.authenticationService.getLastAuthentication();
    	authenticationPromise.then(authentication => this.isAuthenticated = authentication.isAuthenticated);
	}

}
