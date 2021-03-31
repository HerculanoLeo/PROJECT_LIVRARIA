import {MessageState} from '../../../interfaces/Message';
import {CLEAN_MESSAGE, MessageDispatchTypes, SEND_MESSAGE} from './actionMessageTypes';

export function successMessage(message: string, isPersistenceMessage: boolean = false): MessageDispatchTypes {
  const payload: MessageState = {
    type: 'SUCCESS_MESSAGE',
    isPersistenceMessage,
    message,
  };
  return sendMessage(payload);
}

export function errorMessage(message: string, isPersistenceMessage: boolean = false): MessageDispatchTypes {
  const payload: MessageState = {
    type: 'ERROR_MESSAGE',
    isPersistenceMessage,
    message,
  };
  return sendMessage(payload);
}

export function warnMessage(message: string, isPersistenceMessage: boolean = false): MessageDispatchTypes {
  const payload: MessageState = {
    type: 'WARN_MESSAGE',
    isPersistenceMessage,
    message,
  };
  return sendMessage(payload);
}

export function infoMessage(message: string, isPersistenceMessage: boolean = false): MessageDispatchTypes {
  const payload: MessageState = {
    type: 'INFO_MESSAGE',
    isPersistenceMessage,
    message,
  };
  return sendMessage(payload);
}

export function sendMessage(payload: MessageState): MessageDispatchTypes {
  return {type: SEND_MESSAGE, payload};
}

export function cleanMessage(): MessageDispatchTypes {
  return {type: CLEAN_MESSAGE, payload: {} as MessageState};
}
