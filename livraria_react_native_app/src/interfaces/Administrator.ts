import Library from "./Library";
import { Profile } from "./User";

export default interface Administrator {
  id: number;
  nome: string;
  documento: string
  email: string;
  tipo: string;
  tipoDescricao: string;
  perfil: Profile;
  bibliotecas?: Library
}