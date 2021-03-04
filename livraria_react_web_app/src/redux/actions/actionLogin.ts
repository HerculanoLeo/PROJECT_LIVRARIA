import { api } from "../../services/api";
import { userService } from "../../services/userService";
import { LoginDispatch } from "../dispatchTypes/authenticationDispatchTypes";
import { LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS } from "./actionLoginTypes";

export function loginRequest(email: string, password: string) {
  return (dispatch: LoginDispatch) => {
    dispatch({ type: LOGIN_REQUEST });

    userService.login({ email, password }).then(
      (user) => {
        dispatch({ type: LOGIN_SUCCESS, payload: user });
      },
      (error) => {
        dispatch({ type: LOGIN_FAILURE });
      }
    );
  };
}
