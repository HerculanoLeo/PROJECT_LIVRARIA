import axios from 'axios';
import { API } from '../constants';
import { logoutRequest } from '../redux/actions/Login';
import { warnMessage } from '../redux/actions/Message';
import store from '../redux/store'

export const api = axios.create({ baseURL: API.baseUrl, headers: API.baseHeaders, timeout: 10000 });

api.interceptors.response.use((response) => {
  return response;
}, (error) => {
  if (error.response) {
    console.log(error.response.data);
    console.log(error.response.status);
    console.log(error.response.headers);

    if (error.response.status === 403) {
      store.dispatch(logoutRequest());

      store.dispatch(warnMessage('Seção expirada.', true));
    }
  } else if (error.request) {
    if (error.code === 'ECONNABORTED') {
      throw new Error('Servidor fora do ar, tente novamente mais tarde.');
    }

    throw new Error('Verifique sua conexão.');
  } else {
    // Something happened in setting up the request that triggered an Error
    console.log("Error", error.message);
  }

  throw error;
})