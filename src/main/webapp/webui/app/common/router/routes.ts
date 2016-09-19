import {provideRouter, RouterConfig}  from '@angular/router';
import {AuthenticationComponent} from '../../authentication/authentication.component';
import {HomeComponent} from '../../home/home.component';
import {TradeItemListComponent} from '../../trade-item/trade-item-list.component';
import {TradeItemEditorComponent} from '../../trade-item/trade-item-editor.component';
import {UserProfileEditorComponent} from '../../user/user-profile-editor.component';
import {SIMPLE_ROUTES} from './simple-routes';


export const ROUTES = {
  AUTHENTICATION: SIMPLE_ROUTES.AUTHENTICATION,
  AUTHENTICATION_CALLBACK: SIMPLE_ROUTES.AUTHENTICATION + "/:status",
  HOME: SIMPLE_ROUTES.HOME,
  TRADE_ITEM_LIST: SIMPLE_ROUTES.TRADE_ITEM_LIST,
  TRADE_ITEM_EDIT: SIMPLE_ROUTES.TRADE_ITEM_EDIT + "/:tradeItemId",
  TRADE_ITEM_NEW: SIMPLE_ROUTES.TRADE_ITEM_NEW,
  USER_PROFILE_EDIT: SIMPLE_ROUTES.USER_PROFILE_EDIT,
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

