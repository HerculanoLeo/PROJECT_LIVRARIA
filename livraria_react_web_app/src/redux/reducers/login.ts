import { AuthenticationUserState } from "../../interfaces/User/authenticationUserState";
import { LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT } from "../actions/actionLoginTypes";
import { AuthenticationRequestDispatchTypes, AuthenticationSuccessDispatchTypes } from "../dispatchTypes/authenticationDispatchTypes";

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
        tipo: payload.tipo,
        token: payload.token,
        expireToken: new Date(payload.expireToken),
        usuario: payload.usuario,
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
