import {MessageState} from '../../interfaces/Message';
import { CLEAN_MESSAGE, MessageDispatchTypes, SEND_MESSAGE } from '../actions/Message/actionMessageTypes';

const intialState: MessageState = {} as MessageState;

export default function MessageReducer(state: MessageState = intialState, action: MessageDispatchTypes): MessageState {
  switch (action.type) {
    case SEND_MESSAGE: {
      return {
        type: action.payload.type,
        isPersistenceMessage: action.payload.isPersistenceMessage,
        message: action.payload.message,
      };
    }

    case CLEAN_MESSAGE: {
      return intialState;
    }

    default: {
      return state;
    }
  }
}
