import { AuthenticationUserState } from '../../interfaces/User';
import { api } from '../../services/api';
import {
  AuthenticationRequestDispatchTypes,
  AuthenticationSuccessDispatchTypes,
  AuthenticationUpdateUserDispatchTypes,
  LOGIN_FAILURE,
  LOGIN_SUCCESS,
  LOGOUT,
  UPDATE_USER
} from '../actions/Login/actionLoginTypes';

const initialState: AuthenticationUserState = {} as AuthenticationUserState;

export default function AuthenticationReducer(
  state: AuthenticationUserState = initialState, action: AuthenticationSuccessDispatchTypes | AuthenticationRequestDispatchTypes | AuthenticationUpdateUserDispatchTypes): AuthenticationUserState {
  switch (action.type) {
    case LOGIN_SUCCESS: {
      const payload = (<AuthenticationSuccessDispatchTypes>action).payload;

      api.defaults.headers['Authorization'] = `${payload.tipo}${payload.token}`;

      const AuthenticationUserState: AuthenticationUserState = {
        tipo: payload.tipo,
        token: payload.token,
        expireToken: new Date(payload.expireToken),
        usuario: payload.usuario,
      };
      return AuthenticationUserState;
    }

    case LOGIN_FAILURE: {
      return initialState;
    }

    case LOGOUT: {
      api.defaults.headers['Authorization'] = null;

      return initialState;
    }

    case UPDATE_USER: {
      const user = (<AuthenticationUpdateUserDispatchTypes>action).payload;

      return { ...state, usuario: user }
    }

    default: {
      return state;
    }
  }
}
