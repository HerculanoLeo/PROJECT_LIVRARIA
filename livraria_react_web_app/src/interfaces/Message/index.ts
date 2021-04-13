export interface MessageState {
  type: string;
  messages: string[];
}

export interface MessageProps {
  type: string;
  message: string;
}

export const MESSAGE_SUCCESS = "MESSAGE_SUCCESS";
export const MESSAGE_ERROR = "MESSAGE_ERROR";
export const MESSAGE_WARN = "MESSAGE_WARN";
export const MESSAGE_INFO = "MESSAGE_INFO";
