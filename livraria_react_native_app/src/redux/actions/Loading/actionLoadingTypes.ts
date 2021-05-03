export const LOADING_START = 'LOADING_START';
export const LOADING_END = 'LOADING_END';

export interface LoadingState {
  isLoading: boolean
}

export type LoadingDispatchTypes = {
  type: string;
};

export type LoadingDispatch = (args: LoadingDispatchTypes) => LoadingDispatchTypes;