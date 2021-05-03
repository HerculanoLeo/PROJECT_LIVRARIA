import { LoadingDispatchTypes, LoadingState, LOADING_END, LOADING_START } from "../actions/Loading/actionLoadingTypes";

const initialState: LoadingState = {
  isLoading: true,
}


export default function LoadingReducer(state: LoadingState = initialState, action: LoadingDispatchTypes): LoadingState {
  switch (action.type) {
    case LOADING_START: {
      return {
        isLoading: true,
      };
    }

    case LOADING_END: {
      return { isLoading: false };
    }

    default: {
      return state;
    }
  }
}