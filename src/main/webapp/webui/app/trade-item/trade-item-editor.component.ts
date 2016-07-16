import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';

import {TradeItem} from './trade-item';
import {TradeItemService} from './trade-item.service';

@Component({
  selector: 'trade-item-list',
  providers: [TradeItemService],
  templateUrl: 'app/trade-item/trade-item-editor.html'
})
export class TradeItemEditorComponent implements OnInit {
  private tradeItem: TradeItem;
  private toogleClass: string = 'disabled';
  private uiState: number = 0; // bitwase | 0 = clean; 1 = dirty; 2 = saved;

  constructor(private route: ActivatedRoute, private tradeItemService: TradeItemService) { }

  public ngOnInit() {
    let tradeItemId = +this.route.snapshot.params['tradeItemId'];
    this.tradeItemService.get(tradeItemId)
      .then(response => this.tradeItem = response)
      .catch(error => console.log(error));

  }

  private save(t: TradeItem): void {
    if (!(this.uiState == 1)) {
      return;
    }
    this.tradeItemService.save(t)
      .then(response => {
        this.tradeItem = response;
        this.setUiState(2);
      }
      ).catch(error => console.log(error));
  }

  private setUiState(n: number): void {
    if ( n == 0 || n == 2) {
      this.toogleClass = 'disabled';
    } else if ( n == 1) {
      this.toogleClass = 'enabled';
    }

    this.uiState = n;
  }

}
