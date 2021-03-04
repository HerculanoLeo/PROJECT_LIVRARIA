import { User } from "./user";

export interface AuthenticationUserState {
    loggingIn: boolean
    tipo?: string;
    token?: string;
    expireToken?: Date;
    usuario?: User
}