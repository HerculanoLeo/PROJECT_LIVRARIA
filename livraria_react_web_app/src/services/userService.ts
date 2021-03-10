import { api } from "./api";
import { LoginRequest, LoginResponse } from "../interfaces/User/login";
import { ApiError } from '../interfaces/ErroResponse'

export const userService = {
  login,
};

async function login(loginRequest: LoginRequest): Promise<LoginResponse> {
  try {
    const loginResponse = await api.post<LoginResponse>("auth", loginRequest);

    return loginResponse.data;
  } catch (error) {
    if (error.response) {
      // The request was made and the server responded with a status code
      // that falls out of the range of 2xx

      const serverResponse = error.response.data as ApiError;

      console.log(serverResponse.apierror.message);
      console.log(error.response.status);
      console.log(error.response.headers);

      throw new Error(serverResponse.apierror.message);
    } else if (error.request) {
      // The request was made but no response was received
      // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
      // http.ClientRequest in node.js
      console.log(error.request);
    } else {
      // Something happened in setting up the request that triggered an Error
      console.log("Error", error.message);
    }
    console.log(error.config);

    throw new Error("Teste de Erro");
  }
}
