import { combineReducers } from "redux";

import { ToastState } from "../../interfaces/Toast/toast";
import { AuthenticationUserState } from "../../interfaces/User/authenticationUserState";
import AuthenticationReducer from "./login";
import ToastReducer from "./toast";

export interface ApplicationState {
  authentication: AuthenticationUserState;
  toast: ToastState;
}

export const rootReducer = combineReducers({
  authentication: AuthenticationReducer,
  toast: ToastReducer,
});
