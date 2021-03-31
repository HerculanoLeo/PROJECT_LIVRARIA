import React, { createContext, useReducer } from 'react';

import { RegisterClientAction, RegisterClientData, RegisterClientDataContext } from '../interfaces/Client';

const RegisterClientContext = createContext<RegisterClientDataContext>({} as RegisterClientDataContext);

const initialState = {} as RegisterClientData;

const init = () => {
  return initialState;
}

const reducer = (state: RegisterClientData, action: RegisterClientAction): RegisterClientData => {
  switch (action.type) {

    case ('personalInformation'): {
      return {...state, ...action.data  }
    }

    case ('accessCreditials') : {
      return {...state, ...action.data }
    }

    case ('init') : {
      return init();
    }

    default: {
      return state;
    }
  }
}

const RegisterClientContenxtProvaider: React.FC = ({ children }) => {

  const [state, dispatch] = useReducer(reducer, initialState, init);

  return (
    <RegisterClientContext.Provider value={{ state, dispatch }}>
      {children}
    </RegisterClientContext.Provider>
  );
}

export { RegisterClientContext };

export default RegisterClientContenxtProvaider;