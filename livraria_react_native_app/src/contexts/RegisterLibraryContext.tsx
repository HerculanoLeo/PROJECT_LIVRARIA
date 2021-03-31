import React, { createContext, useReducer } from 'react';

import { RegisterLibraryDataContext, RegisterLibraryData, RegisterLibraryAction } from '../interfaces/Library';

const RegisterLibraryContext = createContext<RegisterLibraryDataContext>({} as RegisterLibraryDataContext);

const initialState = {} as RegisterLibraryData;

const init = () => {
  return initialState;
}

const reducer = (state: RegisterLibraryData, action: RegisterLibraryAction): RegisterLibraryData => {
  switch (action.type) {

    case ('administratorInformation'): {
      return {...state, ...action.data  }
    }

    case ('accessCreditials') : {
      return {...state, ...action.data }
    }

    case ('libraryInformation') : {
      return {...state, ...action.data}
    }

    case ('init') : {
      return init();
    }

    default: {
      return state;
    }
  }
}

const RegisterLibraryContextProvader: React.FC = ({ children }) => {

  const [state, dispatch] = useReducer(reducer, initialState, init);

  return (
    <RegisterLibraryContext.Provider value={{state, dispatch}}>
      {children}
    </RegisterLibraryContext.Provider>
  );
}

export { RegisterLibraryContext }

export default RegisterLibraryContextProvader;