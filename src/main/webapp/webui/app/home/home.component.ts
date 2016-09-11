import {Component} from '@angular/core';
import {AuthenticationComponent} from '../authentication/authentication.component';

@Component({
	selector: 'home',
	templateUrl: 'app/home/home.html'
})
export class HomeComponent {
	private isAuthenticated: boolean = false;

	private authenticate() {
		// Create AuthenticationComponent object without dependency injection (no need of DI) for this case.
		let authenticationComponent: AuthenticationComponent = new AuthenticationComponent(null, null, null);
		// Use same as in AuthenticationComponent.authenticate()
		authenticationComponent.authenticate();
	}

}
