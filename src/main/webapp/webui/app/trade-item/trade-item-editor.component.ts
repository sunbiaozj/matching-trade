import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import {TradeItem} from './trade-item';
import {TradeItemService} from './trade-item.service';

@Component({
  selector: 'trade-item-list',
  providers: [TradeItemService],
  templateUrl: 'app/trade-item/trade-item-editor.html'
})
export class TradeItemEditorComponent implements OnInit {
  private tradeItem: TradeItem;
  
  constructor(private route: ActivatedRoute, private tradeItemService: TradeItemService) { }

  public ngOnInit() {
    let tradeItemId = +this.route.snapshot.params['tradeItemId'];
    this.tradeItemService.get(tradeItemId)
      .then(response => this.tradeItem = response)
      .catch(error => console.log(error));
  }

  private save(t: TradeItem):void {
    this.tradeItemService.save(t)
      .then( response => this.tradeItem = response )
      .catch( error => console.log(error));
  }
}
