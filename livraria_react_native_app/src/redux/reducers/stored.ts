import { FINISH_LOAD_STORED, LOADING_STORED, StoredDispatchTypes, StoredState } from "../actions/Stored/actionStoredTypes";

const initialState: StoredState = {
  isLoadingStored: true,
};

export default function StoredReducer(state: StoredState = initialState, action: StoredDispatchTypes): StoredState {
  switch (action.type) {
    case LOADING_STORED: {
      return {
        isLoadingStored: true,
      };
    }

    case FINISH_LOAD_STORED: {
      return { isLoadingStored: false };
    }

    default: {
      return state;
    }
  }
}
