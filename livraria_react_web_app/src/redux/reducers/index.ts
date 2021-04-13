import { combineReducers } from "redux";

import { AuthenticationUserState } from "../../interfaces/User";
import { ToastState } from "../../interfaces/Toast";
import { MessageState } from "../../interfaces/Message";

import AuthenticationReducer from "./login";
import ToastReducer from "./toast";
import MessageReducer from "./message";

export interface ApplicationState {
  authentication: AuthenticationUserState;
  toast: ToastState;
  message: MessageState
}

export const rootReducer = combineReducers({
  authentication: AuthenticationReducer,
  toast: ToastReducer,
  message: MessageReducer
});
