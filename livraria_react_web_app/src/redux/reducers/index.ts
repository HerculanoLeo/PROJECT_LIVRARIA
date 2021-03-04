import { combineReducers } from "redux";
import { AuthenticationUserState } from "../../interfaces/User/authenticationUserState";
import AuthenticationReducer from "./login";

export interface ApplicationState {
  authentication: AuthenticationUserState;
}

export const rootReducer = combineReducers({
  authentication: AuthenticationReducer,
});
