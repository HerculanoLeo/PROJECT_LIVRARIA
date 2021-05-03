import { combineReducers } from "redux";
import { MessageState } from "../../interfaces/Message";

import { AuthenticationUserState } from "../../interfaces/User";
import { LoadingState } from "../actions/Loading/actionLoadingTypes";
import { StoredState } from "../actions/Stored/actionStoredTypes";
import LoadingReducer from "./loading";
import AuthenticationReducer from "./login";
import MessageReducer from "./message";
import StoredReducer from './stored';

export interface ApplicationState {
  authentication: AuthenticationUserState;
  message: MessageState;
  stored: StoredState;
  loading: LoadingState;
}

export const rootReducer = combineReducers({
  authentication: AuthenticationReducer,
  message: MessageReducer,
  stored: StoredReducer,
  loading: LoadingReducer
});