import { Profile } from "./User";

export default interface Client {
  id: number;
  nome: string;
  documento: string
  email: string;
  tipo: string;
  tipoDescricao: string;
  perfil: Profile;
}

export interface RegisterClientDataContext {
  state: RegisterClientData;
  dispatch(action: RegisterClientAction): void;
}

export interface RegisterClientData {
  nome: string;
  documento: string;
  email: string;
  senha: string;
  confirmeSenha: string;  
}

export type RegisterClientAction = 
  { type: 'personalInformation', data: { nome: string; documento: string; } }
| { type: 'accessCreditials', data: { email: string; senha: string; confirmeSenha: string } }
| { type: 'init'};

export interface RegisterClientRequest {
  nome: string;
  documento: string;
  email: string;
  senha: string;
  confirmeSenha: string;
  idioma: string;  
}