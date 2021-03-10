import { User } from "./user";

export interface AuthenticationUserState {
  loggingIn: boolean;
  autentication?: {
    tipo: string;
    token: string;
    expireToken: Date;
    usuario: User;
  };
}
