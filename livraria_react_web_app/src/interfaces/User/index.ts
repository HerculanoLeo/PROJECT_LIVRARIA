export default interface User {
  id: number;
  nome: string;
  email: string;
  tipo: string;
  tipoDescricao: string;
  perfil: Perfil;
}

export interface Perfil {
  id: number;
  nome: string;
  tipo: string;
  tipoDescricao: String;
  permissoes: Permissao[];
}

export interface Permissao {
  codigo: string;
}

export interface AuthenticationUserState {
  tipo: string;
  token: string;
  expireToken: Date;
  usuario: User
}
