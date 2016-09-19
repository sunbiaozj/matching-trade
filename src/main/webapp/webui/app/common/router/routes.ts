import {provideRouter, RouterConfig}  from '@angular/router';
import {AuthenticationComponent} from '../../authentication/authentication.component';
import {HomeComponent} from '../../home/home.component';
import {TradeItemListComponent} from '../../trade-item/trade-item-list.component';
import {TradeItemEditorComponent} from '../../trade-item/trade-item-editor.component';
import {UserProfileEditorComponent} from '../../user/user-profile-editor.component'

export const ROUTES = {
  AUTHENTICATION: "authentication",
  AUTHENTICATION_CALLBACK: "authentication/:status",
  HOME: "home",
  TRADE_ITEM_LIST: "trade-item/trade-item-list",
  TRADE_ITEM_EDIT: "trade-item/trade-item-editor/:tradeItemId",
  TRADE_ITEM_NEW: "trade-item/trade-item-editor",
  USER_PROFILE_EDIT: "user/profile-editor",
};

const routes: RouterConfig = [
  {
    path: '',
    redirectTo: ROUTES.HOME,
    pathMatch: 'full'
  }, {
    path: ROUTES.AUTHENTICATION,
    component: AuthenticationComponent
  }, {
    path: ROUTES.AUTHENTICATION_CALLBACK,
    component: AuthenticationComponent
  }, {
    path: ROUTES.HOME,
    component: HomeComponent
  }, {
    path: ROUTES.TRADE_ITEM_LIST,
    component: TradeItemListComponent
  }, {
    path: ROUTES.TRADE_ITEM_EDIT,
    component: TradeItemEditorComponent
  }, {
    path: ROUTES.TRADE_ITEM_NEW,
    component: TradeItemEditorComponent
  }, {
    path: ROUTES.USER_PROFILE_EDIT,
    component: UserProfileEditorComponent
  }
];

export const appRouterProviders = [
  provideRouter(routes)
];

