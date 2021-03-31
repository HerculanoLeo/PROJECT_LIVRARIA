import { api } from './api';
import User, {
  ChangePasswordRequest,
  ResetPasswordWithCodeRequest,
  ValidateResetPasswordCodeRequest,
  ValidateResetPasswordCodeResponse
} from '../interfaces/User';
import ApiError from '../interfaces/ApiError';
import Client, { RegisterClientRequest } from '../interfaces/Client';

export default {
  registerClient,
  findById,
  changePassword,
  validateResetPasswordCode,
  resetPasswordWithCode
}

async function registerClient(entityRequest: RegisterClientRequest) {
  try {
    const response = await api.post<Client>('usuario', entityRequest);

    return response.data;
  } catch (error) {
    if (error.response) {
      const dataResponse: ApiError = error.response.data as ApiError;

      throw new Error(dataResponse.apierror.message);
    }

    throw error;
  }
}

async function findById(id: number) {
  try {
    const response = await api.get<User>(`usuario/${id}`);

    return response.data;
  } catch (error) {
    if (error.response) {
      if (error.response.status === 404) {
        throw new Error('Usuário não encontrado.')
      }
    }

    throw error;
  }
}

async function changePassword(entityRequest: ChangePasswordRequest) {
  try {
    const response = await api.post<void>('usuario/troca_senha', entityRequest);

    return response;
  } catch (error) {
    if (error.response) {
      if (error.response.status === 404) {
        throw new Error('E-mail não cadastrado.')
      }
    }

    throw error;
  }
}

async function validateResetPasswordCode(entityRequest: ValidateResetPasswordCodeRequest) {
  try {
    const response = await api.post<ValidateResetPasswordCodeResponse>('usuario/troca_senha/valida_codigo', entityRequest);

    return response.data;
  } catch (error) {
    if (error.response) {
      if (error.response.status === 404) {
        throw new Error('E-mail não cadastrado.');
      }

      const dataResponse: ApiError = error.response.data as ApiError;

      throw new Error(dataResponse.apierror.message);
    }

    throw error;
  }
}

async function resetPasswordWithCode(entityRequest: ResetPasswordWithCodeRequest) {
  try {
    const response = await api.post<void>('usuario/troca_senha/codigo', entityRequest);

    return response;
  } catch (error) {
    if (error.response) {
      if (error.response.status === 404) {
        throw new Error('E-mail não cadastrado.')
      }

      const dataResponse: ApiError = error.response.data as ApiError;

      throw new Error(dataResponse.apierror.message)
    }

    throw error;
  }
}