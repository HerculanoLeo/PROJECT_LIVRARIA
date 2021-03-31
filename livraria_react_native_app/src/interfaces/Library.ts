import Administrator from "./Administrator";

export default interface Library {
  id: number;
  nome: string;
  administrador: Administrator;
  operadores?: [];
}

export interface LibratyResponse {
  id: number;
  nome: string;
}

export interface LibratyWithAdministratorResponse {
  id: number;
  nome: string;
  administrador: Administrator;
}

export interface RegisterLibraryWithAdministratorRequest {
  nome: string;
  documento: string;
  email: string;
  senha: string;
  confirmeSenha: string;
  nomeBiblioteca: string;
  idioma: string;
}

export interface RegisterLibraryDataContext {
  state: RegisterLibraryData;
  dispatch(action: RegisterLibraryAction): void;
}

export interface RegisterLibraryData {
  nome: string;
  documento: string;
  email: string;
  senha: string;
  confirmeSenha: string;
  nomeBiblioteca: string;
}

export type RegisterLibraryAction =
  { type: 'administratorInformation', data: { nome: string; documento: string; } }
  | { type: 'libraryInformation', data: { nomeBiblioteca: string } }
  | { type: 'accessCreditials', data: { email: string; senha: string; confirmeSenha: string } }
  | { type: 'init' };
