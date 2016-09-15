import {ActivatedRoute} from '@angular/router';
import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {ROUTE_URLS} from '../app.routes';
import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';
import {FORM_DIRECTIVES, REACTIVE_FORM_DIRECTIVES, FormBuilder, FormGroup, FormControl, Validators} from '@angular/forms';

import {AuthenticationService} from '../authentication/authentication.service';
import {MessangerService} from '../common/messenger.service';
import {User} from './user';
import {UserService} from './user.service';


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
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder,
    private location: Location,
    private messangerService: MessangerService,
    private route: ActivatedRoute,
    private userService: UserService) {    
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
    this.messangerService.setMessage("Account details saved.");
  }

  public ngOnInit() {
    console.log("UserProfileEditorComponent.onInit()");
    // We do not want to use this.authenticationService.getLastAuthentication() in this case because we want the most udpated data from the server on this component
    let authenticationPromise = this.authenticationService.get();
    authenticationPromise.then(authentication => {
      this.userId = authentication.userId;
      this.email = authentication.email;
      this.nameFormControl.updateValue(authentication.name);
       console.log("UserName" + authentication.name);
    });
  }

}
