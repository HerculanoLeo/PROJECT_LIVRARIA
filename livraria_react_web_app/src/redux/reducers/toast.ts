import { ToastState } from "../../interfaces/Toast/toast";
import { TOAST_ERROR, TOAST_HIDE, TOAST_INFO, TOAST_SHOW, TOAST_SUCCESS, TOAST_WARN } from "../actions/Toast/actionToastTypes";

export type ToastDispatchTypes = {
  type: string;
  payload?: ToastMessage;
};

export type ToastMessage = {
  message: string;
  time?: number;
};

export type ToastDispatch = (arg: ToastDispatchTypes) => ToastDispatchTypes;

const initialState: ToastState = {} as ToastState;

export default function ToastReducer(state: ToastState = initialState, action: ToastDispatchTypes): ToastState {
  switch (action.type) {
    case TOAST_SHOW: {
      return {
        ...state,
        show: true,
      };
    }

    case TOAST_HIDE: {
      return {
        show: false,
      } as ToastState;
    }

    case TOAST_SUCCESS: {
      return {
        show: false,
        type: TOAST_SUCCESS,
        message: action.payload?.message || "",
        time: action.payload?.time || 5,
      };
    }

    case TOAST_INFO: {
      return {
        show: false,
        type: TOAST_INFO,
        message: action.payload?.message || "",
        time: action.payload?.time || 5,
      };
    }

    case TOAST_ERROR: {
      return {
        show: false,
        type: TOAST_ERROR,
        message: action.payload?.message || "",
        time: action.payload?.time || 5,
      };
    }

    case TOAST_WARN: {
      return {
        show: false,
        type: TOAST_WARN,
        message: action.payload?.message || "",
        time: action.payload?.time || 5,
      };
    }

    default : {
      return state;
    }
  }
}
