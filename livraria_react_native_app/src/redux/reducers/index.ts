import { combineReducers } from "redux";
import { MessageState } from "../../interfaces/Message";

import { AuthenticationUserState } from "../../interfaces/User";
import { StoredState } from "../actions/Stored/actionStoredTypes";
import AuthenticationReducer from "./login";
import MessageReducer from "./message";
import StoredReducer from './stored';

export interface ApplicationState {
  authentication: AuthenticationUserState;
  message: MessageState;
  stored: StoredState
}

export const rootReducer = combineReducers({
  authentication: AuthenticationReducer,
  message: MessageReducer,
  stored: StoredReducer
});