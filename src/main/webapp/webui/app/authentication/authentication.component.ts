import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
// import {ROUTE_URLS} from '../app.routes';

import {MessangerService} from '../common/messenger.service';

import {AuthenticationService} from './authentication.service';

@Component({
  selector: 'authentication',
  templateUrl: 'app/authentication/authentication.html'
})
export class AuthenticationComponent implements OnInit {
  private authenticationStatus: string;
  private isAuthenticated: boolean;
  private userName: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService) { }

  ngOnInit() {
    // get authentication/:status from URL parameter
    this.authenticationStatus = this.route.snapshot.params['status'];

    let authenticationPromise = this.authenticationService.getLastAuthentication();
    authenticationPromise.then(authentication => {
      this.isAuthenticated = authentication.isAuthenticated;
      this.userName = authentication.name;
    });

    // new-user: user has been redirected from authentication and is a new user. Let's redirect it to the profile page.
    // existing-user: user has been redirected from authentication and is an existing user. Do nothing for now, it can be useful in the future.
    // sign-in: user wants to Sign-in. Do nothing here because the user will be presented with 'authentication.html'.
    // sign-out: user wants to Sing-out. Redirect to /authenticate/sign-out
    // switch (this.authenticationStatus) {
    //   case 'new-user':
    //     this.router.navigate([ROUTE_URLS.USER_PROFILE_EDIT]);
    //     break;
    //   case 'existing-user':
    //     this.router.navigate([ROUTE_URLS.TRADE_ITEM_LIST]);
    //     break;                      
    //   default:
    //     break;
    // }

  }

  public authenticate(): void {
    window.location.assign("/authenticate");
  }

}
