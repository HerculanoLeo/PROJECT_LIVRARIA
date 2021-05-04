import AutenticacaoService from '../../../services/AuthenticationService';
import UserService from '../../../services/UserService';
import store from '../../store';
import { endLoading, startLoading } from '../Loading';
import { errorMessage } from '../Message';
import { removeUserDateFromStored, saveUserDateFromStored } from '../Stored';
import {
  LOGIN_SUCCESS,
  LOGOUT,
  UPDATE_USER,
} from './actionLoginTypes';

export function loginRequest(email: string, password: string, rememberMe: boolean) {
  return async (dispatch: any) => {
    try {
      dispatch(startLoading({ isBlockScreen: true, isActivityIndicator: true }));

      const user = await AutenticacaoService.login({ email, password });

      if (rememberMe) {
        saveUserDateFromStored(user);
      }

      dispatch({ type: LOGIN_SUCCESS, payload: user });
    } catch (error) {
      dispatch(errorMessage(error.message, false));
    } finally {
      dispatch(endLoading());
    }
  }
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
  return async (dispatch: any) => {
    await removeUserDateFromStored();

    return dispatch({ type: LOGOUT });
  }
}
