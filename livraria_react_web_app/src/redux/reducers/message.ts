import { MessageProps , MessageState } from "../../interfaces/Message";
import { MESSAGE_ADD, MESSAGE_CLEAN } from "../actions/Message/actionMessageTypes";

export type MessageDispatchTypes = {
  type: string;
  payload: MessageProps;
};

export type MessageDispatch = (arg: MessageDispatchTypes) => MessageDispatchTypes;

const initialState: MessageState = {
  type: "MESSAGE_INFO",
  messages: []
};

export default function MessageReducer(state: MessageState = initialState, action: MessageDispatchTypes): MessageState {
  switch (action.type) {
    case MESSAGE_ADD: {
      let messages = state.messages.concat(action.payload.message);

      return {
        type: action.payload.type,
        messages: messages,
      };
    }

    case MESSAGE_CLEAN: {
     return initialState;
    }

    default: {
      return state;
    }
  }
}
