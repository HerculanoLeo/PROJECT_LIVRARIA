export interface ToastState {
  toasts: ToastProps[];
}

export interface ToastProps {
  uuid: string;
  time: number;
  message: string;
  type: string;
}

export const TOAST_SUCCESS = "TOAST_SUCCESS";
export const TOAST_ERROR = "TOAST_ERROR";
export const TOAST_WARN = "TOAST_WARN";
export const TOAST_INFO = "TOAST_INFO";
