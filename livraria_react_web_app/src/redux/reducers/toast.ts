import { ToastProps, ToastState } from "../../interfaces/Toast";
import { TOAST_ADD, TOAST_REMOVE } from "../actions/Toast/actionToastTypes";

export type ToastDispatchTypes = {
  type: string;
  payload: ToastProps;
};

export type ToastDispatch = (arg: ToastDispatchTypes) => ToastDispatchTypes;

const initialState: ToastState = {
  toasts: [],
};

export default function ToastReducer(state: ToastState = initialState, action: ToastDispatchTypes): ToastState {
  switch (action.type) {
    case TOAST_ADD: {
      let toasts = state.toasts.concat(action.payload);

      return {
        toasts: toasts,
      };
    }

    case TOAST_REMOVE: {
      const toasts = state.toasts.filter((value) => {
        return value.uuid !== action.payload.uuid;
      });

      return {
        toasts: toasts,
      };
    }

    default: {
      return state;
    }
  }
}
