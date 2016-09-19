import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';

import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';

import {FORM_DIRECTIVES, REACTIVE_FORM_DIRECTIVES, FormBuilder, FormGroup, FormControl, Validators} from '@angular/forms';

import {TradeItem} from './trade-item';
import {TradeItemService} from './trade-item.service';
import {MessengerService} from '../common/messenger/messenger.service';
import {RouterService} from '../common/router/router.service';

@Component({
  selector: 'trade-item-list',
  providers: [TradeItemService],
  directives: [FORM_DIRECTIVES, REACTIVE_FORM_DIRECTIVES],
  templateUrl: 'app/trade-item/trade-item-editor.html'
})
export class TradeItemEditorComponent implements OnInit {
  private descriptionFormControl: FormControl = new FormControl();
  private nameFormControl: FormControl = new FormControl('', Validators.required);
  private formGroup: FormGroup;
  private tradeItemId: number;

  constructor(
    private location: Location,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private routerService: RouterService,
    private tradeItemService: TradeItemService,
    private messengerService: MessengerService) {    
    this.formGroup = new FormGroup({
        description: this.descriptionFormControl,
        name: this.nameFormControl
    });
  }

  private navigate(destination: string): void {
    let link: any;
    switch (destination) {
      case 'cancel':
      case 'trade-item-list':
        link = [this.routerService.routes.TRADE_ITEM_LIST];
    }
    this.routerService.navigate(link);
  }

  /**
   * GET TradeItem based on URL parameter tradeItemId.
   * Loads formGroup with their corresponding values. 
   */
  public ngOnInit() {
    // GET TradeItem based on URL parameter tradeItemId.
    this.tradeItemId = +this.route.snapshot.params['tradeItemId'];
    if (!this.tradeItemId) {
      return;
    }
    this.tradeItemService
      .get(this.tradeItemId)
      .then(response => {
        this.loadFormGroupFromTradeItem(response);
      })
      .catch(error => console.log(error));
  }

  private onSubmit() {
    if (!this.formGroup.valid) {
      return;
    }
    let tradeItem: TradeItem = this.transformFormGroupToTradeItem(this.formGroup);
    this.save(tradeItem);
    this.messengerService.setMessage("Item saved.");
    // TODO: Reset formGroup when new Angular 2 version is available. See: https://github.com/angular/angular/pull/9974
    this.navigate('trade-item-list');
  }

  private save(t: TradeItem): void {
    this.tradeItemService.save(t)
      .then(response => {
        this.loadFormGroupFromTradeItem(response);
      }).catch(error => console.log(error));
  }

  private transformFormGroupToTradeItem(f: FormGroup): TradeItem {
    let result: TradeItem = new TradeItem();
    result.tradeItemId = this.tradeItemId;
    result.description = this.descriptionFormControl.value;
    result.name = this.nameFormControl.value;
    return result;
  }

  private loadFormGroupFromTradeItem(t: TradeItem) {
    this.tradeItemId = t.tradeItemId;
    this.descriptionFormControl.updateValue(t.description);
    this.nameFormControl.updateValue(t.name);
  }

}
