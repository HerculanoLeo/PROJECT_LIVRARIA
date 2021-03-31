import { FINISH_LOAD_STORED, LOADING_STORED, StoredDispatchTypes, StoredState } from "../actions/Stored/actionStoredTypes";

const intialState: StoredState = {
  isLoading: true,
};

export default function StoredReducer(state: StoredState = intialState, action: StoredDispatchTypes): StoredState {
  switch (action.type) {
    case LOADING_STORED: {
      return {
        isLoading: true,
      };
    }

    case FINISH_LOAD_STORED: {
      return { isLoading: false };
    }

    default: {
      return state;
    }
  }
}
