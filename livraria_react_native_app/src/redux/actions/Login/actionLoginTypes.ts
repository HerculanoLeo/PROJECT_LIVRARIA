import { LoginResponse } from '../../../interfaces/Login';
import User from '../../../interfaces/User';

export type AuthenticationSuccessDispatchTypes = {
  type: string;
  payload: LoginResponse;
};

export type AuthenticationRequestDispatchTypes = {
  type: string;
};

export type AuthenticationUpdateUserDispatchTypes = {
  type: string;
  payload: User
};

export type LoginDispatch = (
  args: AuthenticationSuccessDispatchTypes | AuthenticationRequestDispatchTypes,
) => AuthenticationSuccessDispatchTypes;

export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';
export const UPDATE_USER = 'UPDATE_USER';
export const LOGOUT = 'LOGOUT';
