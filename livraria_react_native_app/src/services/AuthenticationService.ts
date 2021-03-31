import { api } from "./api";
import { LoginRequest, LoginResponse } from "../interfaces/Login";
import ApiError from "../interfaces/ApiError";

export default {
  login,
};

async function login(loginRequest: LoginRequest): Promise<LoginResponse> {
  try {
    const loginResponse = await api.post<LoginResponse>("auth", loginRequest);

    return loginResponse.data;
  } catch (error) {
    if (error.response) {
      const dataResponse: ApiError = error.response.data as ApiError;

      throw new Error(dataResponse.apierror.message)
    } else {
      throw new Error(error.message)
    }
  }
}