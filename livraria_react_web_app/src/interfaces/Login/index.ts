import User from "../User";

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  tipo: string;
  token: string;
  expireToken: string;
  usuario: User;
}
