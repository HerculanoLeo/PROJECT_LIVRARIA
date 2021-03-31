import {MessageState} from '../../../interfaces/Message';

export type MessageDispatchTypes = {
  type: string;
  payload: MessageState;
};

export type MessageDispatch = (args: MessageDispatchTypes) => MessageDispatchTypes;

export const SEND_MESSAGE = 'SEND_MESSAGE';
export const CLEAN_MESSAGE = 'CLEAN_MESSAGE';
