import { userService } from "../../../services/userService";
import { LoginDispatch } from "../../reducers/login";
import { toastError } from "../Toast/actionToast";
import { LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT } from "./actionLoginTypes";

export function loginRequest(email: string, password: string) {
  return (dispatch: LoginDispatch | any) => {
    dispatch({ type: LOGIN_REQUEST });

    userService.login({ email, password }).then(
      (user) => {
        dispatch({ type: LOGIN_SUCCESS, payload: user });
      },
      (error) => {
        dispatch({ type: LOGIN_FAILURE });
        dispatch(toastError(error.message));
      }
    );
  };
}

export function logoutRequest() {
  return (dispatch: LoginDispatch) => {
    dispatch({type: LOGOUT});
  }
}
