import {Component, Injectable, Inject} from '@angular/core';
import {Router} from '@angular/router';
import {SIMPLE_ROUTES} from './simple-routes';

import {AuthenticationService} from '../../authentication/authentication.service';

@Injectable()
export class RouterService {

  public routes: any = SIMPLE_ROUTES;

  constructor(
    // @Inject(AuthenticationService) private authenticationService: AuthenticationService,
    // @Inject(Router) private router: Router
    private authenticationService: AuthenticationService,
    private router: Router
  ) { }

  /**
   * Navigates to the target destination.
   * If is a public destination or the user is authenticated, then go to the target destination.
   * Otherwise, go to the HOME page.
   */
  public navigate(destination: any) {
    let authenticationPromise = this.authenticationService.getLastAuthentication();
    authenticationPromise.then(authentication => {
      let isAuthenticated: boolean = authentication.isAuthenticated;
      let isNavigatingToPublicDestination: boolean = false;

      switch (destination) {
        case this.routes.AUTHENTICATION:
        case this.routes.HOME:
          isNavigatingToPublicDestination = true;
      }

      if (isAuthenticated || isNavigatingToPublicDestination) {
        this.router.navigate(destination);
      } else {
        this.router.navigate([this.routes.HOME]);
      }
    })
  }

}
