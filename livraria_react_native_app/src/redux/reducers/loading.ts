import { LoadingDispatchTypes, LoadingState, LOADING_END, LOADING_START } from "../actions/Loading/actionLoadingTypes";

const initialState: LoadingState = {
  isLoading: false,
  isActivityIndicator: false,
  isBlockScreen: false,
}

export default function LoadingReducer(state: LoadingState = initialState, action: LoadingDispatchTypes): LoadingState {
  switch (action.type) {
    case LOADING_START: {
      return {
        isLoading: true,
        isActivityIndicator: action.payload.isActivityIndicator,
        isBlockScreen: action.payload.isBlockScreen
      };
    }

    case LOADING_END: {
      return { isLoading: false , isActivityIndicator: false, isBlockScreen: false};
    }

    default: {
      return state;
    }
  }
}