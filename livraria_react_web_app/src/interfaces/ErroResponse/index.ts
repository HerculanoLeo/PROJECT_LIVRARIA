export type ApiError = {
  apierror: {
    status: string;
    timestamp: string;
    message: string;
    debugMessage: string;
    subErrors: Object | null;
  };
};
