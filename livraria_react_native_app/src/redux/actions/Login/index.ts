import AutenticacaoService from '../../../services/AuthenticationService';
import UserService from '../../../services/UserService';
import store from '../../store';
import { errorMessage } from '../Message';
import { saveUserDateFromStored } from '../Stored';
import {
  LoginDispatch,
  LOGIN_FAILURE,
  LOGIN_SUCCESS,
  LOGOUT,
  UPDATE_USER,
} from './actionLoginTypes';

export function loginRequest(email: string, password: string, rememberMe: boolean) {
  return (dispatch: LoginDispatch) => {
    AutenticacaoService.login({ email, password }).then(user => {
      if (rememberMe) {
        saveUserDateFromStored(user);
      }

      dispatch({ type: LOGIN_SUCCESS, payload: user });
    }).catch((error) => {
      dispatch({ type: LOGIN_FAILURE });
      dispatch(errorMessage(error.message, false));
    });
  };
}

export function updateUserData() {
  return async (dispatch: (action: any) => void) => {
    try {
      const { expireToken, tipo, token, usuario } = store.getState().authentication

      const user = await UserService.findById(usuario.id);

      saveUserDateFromStored({
        expireToken: expireToken.toISOString(),
        tipo,
        token,
        usuario: user
      });

      return dispatch({ type: UPDATE_USER, payload: user });
    } catch (error) {
      if (error.response) {
        return dispatch(logoutRequest());
      }
    }
  }
}

export function logoutRequest() {
  return { type: LOGOUT };
}
