import { AuthenticationUserState } from "../../interfaces/User";
import { AuthenticationRequestDispatchTypes, AuthenticationSuccessDispatchTypes, LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT } from "../actions/Login/actionLoginTypes";


const intialState: AuthenticationUserState = {} as AuthenticationUserState;

export default function AuthenticationReducer(
  state: AuthenticationUserState = intialState,
  action: AuthenticationSuccessDispatchTypes | AuthenticationRequestDispatchTypes
): AuthenticationUserState {
  switch (action.type) {

    case LOGIN_SUCCESS: {
      const payload = (<AuthenticationSuccessDispatchTypes>action).payload;

      const AuthenticationUserState: AuthenticationUserState = {
        tipo: payload.tipo,
        token: payload.token,
        expireToken: new Date(payload.expireToken),
        usuario: payload.usuario,
      };
      return AuthenticationUserState;
    }

    case LOGOUT: {
      return {} as AuthenticationUserState;
    }

    default: {
      return state;
    }
  }
}
