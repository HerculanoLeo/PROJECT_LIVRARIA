export const LOADING_START = 'LOADING_START';
export const LOADING_END = 'LOADING_END';

export interface LoadingState {
  isLoading: boolean;
  isBlockScreen: boolean;
  isActivityIndicator: boolean;
}

export type LoadingDispatchTypes = {
  type: string;
  payload: LoadingPayload;
};

export interface LoadingPayload {
  isBlockScreen: boolean;
  isActivityIndicator: boolean;
}

export type LoadingDispatch = (args: LoadingDispatchTypes) => LoadingDispatchTypes;