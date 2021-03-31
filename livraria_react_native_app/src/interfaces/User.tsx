export default interface User {
  id: number;
  nome: string;
  email: string;
  tipo: string;
  tipoDescricao: string;
  perfil: Profile;
}

export interface Profile {
  id: number;
  nome: string;
  tipo: string;
  tipoDescricao: String;
  permissoes: Permission[]
}

export interface Permission {
  codigo: string
}

export interface AuthenticationUserState {
  tipo: string;
  token: string;
  expireToken: Date;
  usuario: User
}

export interface ChangePasswordRequest {
  email: string;
  novaSenha?: string;
  senhaAntiga?: string;
  confirmaSenha?: string;
}

export interface ValidateResetPasswordCodeRequest {
  email: string;
  code: string;
}

export interface ValidateResetPasswordCodeResponse {
  nome: string;
	email: string;
	dataValidade: string;
}

export interface ResetPasswordWithCodeRequest {
  email: string;	
	code: string;	
	senha: string;	
	confirmaSenha: string;
}