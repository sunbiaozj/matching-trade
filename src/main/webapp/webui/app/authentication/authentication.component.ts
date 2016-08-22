import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {ErrorAppService} from '../common/error-app-service';

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
    private authenticationService: AuthenticationService) { }

  ngOnInit() {
    // get authentication/:status from URL parameter
    this.authenticationStatus = this.route.snapshot.params['status'];

    let authenticationPromise = this.authenticationService.getLastAuthentication();
    authenticationPromise.then(authentication => {
      this.isAuthenticated = authentication.isAuthenticated;
      this.userName = authentication.name;
    });

    // existing-user: user has been redirected from authentication and is an existing user. Do nothing for now, it can be useful in the future.
    // signin: user wants to Sign-in. Do nothing here because the user will be presented with 'authentication.html'.
    // signout: user wants to Sing-out. Redirect to /authenticate/signout
    if (this.authenticationStatus == 'signout') {
      this.singOut();
    }
  }

  public authenticate(): void {
    window.location.assign("/authenticate");
  }

  public singOut(): void {
    window.location.assign("/authenticate/signout");
  }


}
