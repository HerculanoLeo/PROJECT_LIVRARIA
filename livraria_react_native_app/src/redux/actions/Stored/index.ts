import EncryptedStorage from 'react-native-encrypted-storage';

import { FINISH_LOAD_STORED, LOADING_STORED, USER_DATA_STORED_KEY } from './actionStoredTypes';
import { LoginDispatch, LOGIN_SUCCESS, LOGOUT } from '../Login/actionLoginTypes';
import { LoginResponse } from '../../../interfaces/Login';

export async function saveUserDateFromStored(user: LoginResponse) {
  await EncryptedStorage.setItem(USER_DATA_STORED_KEY, JSON.stringify(user));
}

export function loadUserDateFromStored() {
  return async (dispatch: LoginDispatch) => {
    try {
      const value = await EncryptedStorage.getItem(USER_DATA_STORED_KEY);

      if (value) {
        const userDate: LoginResponse = JSON.parse(value);

        return dispatch({ type: LOGIN_SUCCESS, payload: userDate });
      }
    } catch (error) {
      return dispatch({ type: LOGOUT });
    }
  };
}

export function loadStored() {
  return async (dispatch: any) => {
    dispatch({ type: LOADING_STORED });

    await Promise.all([dispatch(loadUserDateFromStored())])
    
    return dispatch({ type: FINISH_LOAD_STORED });
  }
}
