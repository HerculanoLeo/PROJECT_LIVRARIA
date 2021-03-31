export const USER_DATA_STORED_KEY = 'USER_DATA_STORED_KEY';

export const LOADING_STORED = 'LOADING_STORED';
export const FINISH_LOAD_STORED = 'FINISH_LOAD_STORED';

export interface StoredState {
  isLoading: boolean
}

export type StoredDispatchTypes = {
  type: string;
};

export type StoredDispatch = (args: StoredDispatchTypes) => StoredDispatchTypes;
