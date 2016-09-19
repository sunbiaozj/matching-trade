import {ROUTES} from './routes';

/**
 * Contains simple routes (routes without parameters) to be used along RouterService.
 * Do not mistake these routes with ROUTE_URLS which supports URL parameters.
 */
export const SIMPLE_ROUTES = {
  AUTHENTICATION: "authentication",
  AUTHENTICATION_CALLBACK: "authentication",
  HOME: "home",
  TRADE_ITEM_EDIT: "trade-item/trade-item-editor",
  TRADE_ITEM_LIST: "trade-item/trade-item-list",
  TRADE_ITEM_NEW: "trade-item/trade-item-editor",
  USER_PROFILE_EDIT: "user/profile-editor",
};