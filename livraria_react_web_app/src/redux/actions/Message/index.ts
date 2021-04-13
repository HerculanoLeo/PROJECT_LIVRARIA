import { MESSAGE_ERROR, MESSAGE_INFO, MESSAGE_SUCCESS, MESSAGE_WARN } from "../../../interfaces/Message";
import { MessageDispatch } from "../../reducers/message";
import { MESSAGE_ADD, MESSAGE_CLEAN } from "./actionMessageTypes";

export function messageSuccess(message: string) {
  return _message(message, MESSAGE_SUCCESS);
}

export function messageInfo(message: string) {
  return _message(message, MESSAGE_INFO);
}

export function messageError(message: string) {
  return _message(message, MESSAGE_ERROR);
}

export function messageWarn(message: string) {
  return _message(message, MESSAGE_WARN);
}

export function _message(message: string, type: string = MESSAGE_INFO) {
  return (dispatch: MessageDispatch) => {
    dispatch({
      type: MESSAGE_ADD,
      payload: {
        type,
        message,
      },
    });
  };
}

export function cleanMessage() {
  return { type: MESSAGE_CLEAN };
}
