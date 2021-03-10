import { AuthenticationUserState } from "../../interfaces/User/authenticationUserState";
import { LoginResponse } from "../../interfaces/User/login";
import { LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT } from "../actions/Login/actionLoginTypes";

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

const intialState: AuthenticationUserState = {} as AuthenticationUserState;

export default function AuthenticationReducer(
  state: AuthenticationUserState = intialState,
  action: AuthenticationSuccessDispatchTypes | AuthenticationRequestDispatchTypes
): AuthenticationUserState {
  switch (action.type) {
    case LOGIN_REQUEST: {
      return { loggingIn: true };
    }

    case LOGIN_SUCCESS: {
      const payload = (<AuthenticationSuccessDispatchTypes>action).payload;

      const AuthenticationUserState: AuthenticationUserState = {
        loggingIn: true,
        autentication: {
          tipo: payload.tipo,
          token: payload.token,
          expireToken: new Date(payload.expireToken),
          usuario: payload.usuario,
        },
      };
      return AuthenticationUserState;
    }

    case LOGIN_FAILURE: {
      return { loggingIn: false };
    }

    case LOGOUT: {
      return {} as AuthenticationUserState;
    }

    default: {
      return state;
    }
  }
}
