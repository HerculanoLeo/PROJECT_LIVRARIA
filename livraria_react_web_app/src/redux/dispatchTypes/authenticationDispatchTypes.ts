import { LoginResponse } from "../../interfaces/User/login";

export type AuthenticationSuccessDispatchTypes = {
    type: string,
    payload: LoginResponse
}

export type AuthenticationRequestDispatchTypes = {
    type: string
}

export type LoginDispatch = (args: AuthenticationSuccessDispatchTypes | AuthenticationRequestDispatchTypes) => AuthenticationSuccessDispatchTypes;