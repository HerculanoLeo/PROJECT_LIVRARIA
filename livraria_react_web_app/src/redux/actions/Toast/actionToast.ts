import { ToastDispatch } from "../../reducers/toast";
import { TOAST_ERROR, TOAST_HIDE, TOAST_INFO, TOAST_SHOW, TOAST_SUCCESS, TOAST_WARN } from "./actionToastTypes";

export function toastSuccess(message: string, time: number = 5) {
  return toast(message, TOAST_SUCCESS, time);
}

export function toastInfo(message: string, time: number = 5) {
  return toast(message, TOAST_INFO, time);
}

export function toastError(message: string, time: number = 5) {
  return toast( message, TOAST_ERROR, time);
}

export function toastWarn(message: string, time: number = 5) {
  return toast(message, TOAST_WARN, time);
}

export function toast(message: string, type: string = TOAST_INFO, time: number = 5) {
  return (dispatch: ToastDispatch) => {
    dispatch({
      type,
      payload: {
        message,
        time,
      },
    });

    dispatch({ type: TOAST_SHOW });
  }
}
