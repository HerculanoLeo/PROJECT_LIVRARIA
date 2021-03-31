import ApiError from "../interfaces/ApiError";
import { LibratyWithAdministratorResponse, RegisterLibraryWithAdministratorRequest } from "../interfaces/Library";
import { api } from "./api";

export default {
  registerLibraryWithAdministrator
}

async function registerLibraryWithAdministrator(entityRequest: RegisterLibraryWithAdministratorRequest) {
  try {
    const response = await api.post<LibratyWithAdministratorResponse>('biblioteca', entityRequest);

    return response.data;
  } catch (error) {
    if (error.response) {
      const dataResponse: ApiError = error.response.data as ApiError;

      throw new Error(dataResponse.apierror.message);
    }

    throw error;
  }
}