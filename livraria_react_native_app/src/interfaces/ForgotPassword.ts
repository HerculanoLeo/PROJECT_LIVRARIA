export default interface ForgotPasswordDataContext {
  state: ForgotPasswordData;
  dispatch(action: ForgotPasswordAction): void;
}

export interface ForgotPasswordData {
  code: string;
  email: string;
  nome: string;
  dataValidade?: Date
  
}

export interface ValidateResetPasswordCode {
  code: string;
  email: string;
  nome: string;
  dataValidade: Date
}

export type ForgotPasswordAction = { type: 'email', email: string }
  | { type: 'code', data: ValidateResetPasswordCode };