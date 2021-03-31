export interface MessageState {
  type:
  | typeof SUCCESS_MESSAGE
  | typeof WARN_MESSAGE
  | typeof ERROR_MESSAGE
  | typeof INFO_MESSAGE;
  isPersistenceMessage: boolean;
  message: string;
}

export const SUCCESS_MESSAGE = 'SUCCESS_MESSAGE';
export const INFO_MESSAGE = 'INFO_MESSAGE';
export const WARN_MESSAGE = 'WARN_MESSAGE';
export const ERROR_MESSAGE = 'ERROR_MESSAGE';
