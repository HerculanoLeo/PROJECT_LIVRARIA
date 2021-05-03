import {MessageState} from '../../interfaces/Message';
import { CLEAN_MESSAGE, MessageDispatchTypes, SEND_MESSAGE } from '../actions/Message/actionMessageTypes';

const initialState: MessageState = {} as MessageState;

export default function MessageReducer(state: MessageState = initialState, action: MessageDispatchTypes): MessageState {
  switch (action.type) {
    case SEND_MESSAGE: {
      return {
        type: action.payload.type,
        isPersistenceMessage: action.payload.isPersistenceMessage,
        message: action.payload.message,
      };
    }

    case CLEAN_MESSAGE: {
      return initialState;
    }

    default: {
      return state;
    }
  }
}
