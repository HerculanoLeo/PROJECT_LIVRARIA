import React, { createContext, useReducer } from 'react';

import ForgotPasswordDataContext, { ForgotPasswordAction, ForgotPasswordData } from '../interfaces/ForgotPassword';

const ForgotPasswordContext = createContext<ForgotPasswordDataContext>({} as ForgotPasswordDataContext);

const initialState = {} as ForgotPasswordData;

const init = () => {
  return initialState;
}

const reducer = (state: ForgotPasswordData, action: ForgotPasswordAction) => {
  switch (action.type) {
    case ('email'): {
      return { ...state, email: action.email };
    }

    case ('code'): {
      return { ...state, ...action.data }
    }

    default: {
      return state;
    }
  }
}

const ForgotPasswordContextProvaider: React.FC = ({ children }) => {

  const [state, dispatch] = useReducer(reducer, initialState, init);

  return (
    <ForgotPasswordContext.Provider value={{state, dispatch}}>
      {children}
    </ForgotPasswordContext.Provider>
  );
}

export { ForgotPasswordContext };

export default ForgotPasswordContextProvaider;