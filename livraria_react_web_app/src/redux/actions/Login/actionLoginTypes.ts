import { LoginResponse } from "../../../interfaces/Login";

export const LOGIN_REQUEST = "LOGIN_REQUEST";
export const LOGIN_SUCCESS = "LOGIN_SUCCESS";
export const LOGIN_FAILURE = "LOGIN_FAILURE";
export const LOGOUT = "LOGOUT";


export type AuthenticationSuccessDispatchTypes = {
  type: string;
  payload: LoginResponse;
};

export type AuthenticationRequestDispatchTypes = {
  type: string;
};

export type LoginDispatch = (
  args: AuthenticationSuccessDispatchTypes | AuthenticationRequestDispatchTypes
) => AuthenticationSuccessDispatchTypes;