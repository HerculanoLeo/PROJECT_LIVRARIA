import UserService from "../../../services/userService";
import { cleanMessage, messageError } from "../Message";
import { LoginDispatch, LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT } from "./actionLoginTypes";

export function loginRequest(email: string, password: string) {
  return (dispatch: LoginDispatch | any) => {
    dispatch({ type: LOGIN_REQUEST });
    dispatch(cleanMessage())

    UserService.login({ email, password }).then(
      (user) => {
        dispatch({ type: LOGIN_SUCCESS, payload: user });
      },
      (error) => {
        dispatch({ type: LOGIN_FAILURE });
        dispatch(messageError(error.message));
      }
    );
  };
}

export function logoutRequest() {
  return (dispatch: LoginDispatch) => {
    dispatch({type: LOGOUT});
  }
}
