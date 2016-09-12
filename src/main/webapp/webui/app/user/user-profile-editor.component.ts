import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {ROUTE_URLS} from '../app.routes';

import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';

import {AuthenticationService} from '../authentication/authentication.service';

import {User} from './user';
import {UserService} from './user.service';

import {FORM_DIRECTIVES, REACTIVE_FORM_DIRECTIVES, FormBuilder, FormGroup, FormControl, Validators} from '@angular/forms';


@Component({
  selector: 'trade-item-list',
  providers: [UserService],
  directives: [FORM_DIRECTIVES, REACTIVE_FORM_DIRECTIVES],
  templateUrl: 'app/user/user-profile-editor.html'
})
export class UserProfileEditorComponent implements OnInit {
  private userId: number;
  private email: string;

  private formGroup: FormGroup;
  private nameFormControl: FormControl = new FormControl('', Validators.required);
  private emailFormControl: FormControl = new FormControl('');

  constructor(
    private location: Location,
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private formBuilder: FormBuilder) {    
    this.formGroup = new FormGroup({
        name: this.nameFormControl
    });
  }

  private onSubmit(): void {
    let user: User = new User();
    user.userId = this.userId;
    user.email = this.email;
    user.name = this.formGroup.controls["name"].value;
    this.userService.save(user);
  }

  public ngOnInit() {
    console.log("UserProfileEditorComponent.onInit()");
    // We do not want to use this.authenticationService.getLastAuthentication() because we want the most udpated data in this case.
    let authenticationPromise = this.authenticationService.get();
    authenticationPromise.then(authentication => {
      this.userId = authentication.userId;
      this.email = authentication.email;
      this.nameFormControl.updateValue(authentication.name);
       console.log("UserName" + authentication.name);
    });
  }

}
