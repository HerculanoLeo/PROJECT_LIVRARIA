import React, {createContext, useState, useEffect} from 'react';
import * as auth from '../service/auth.service';
import AsyncStored from '@react-native-community/async-storage';

interface AuthContextData {
  signed: boolean;
  user: Object | null;
  signIn(): Promise<void>;
  singOut(): void;
}

const AuthContext = createContext<AuthContextData>({} as AuthContextData);

export const AuthProvaider: React.FC = ({children}) => {
  const [user, setUser] = useState<Object | null>(null);

  useEffect(() => {
    async function loadStoredData() {
      const storedUser = await AsyncStored.getItem('@RNAuth:user');
      const storedToken = await AsyncStored.getItem('@RNAuth:token');

      if(storedUser && storedToken) {
          setUser(JSON.parse(storedUser))
      }
    }

    loadStoredData();
  }, []);

  const signIn = async () => {
    const response = await auth.SingIn();

    setUser(response.user);

    await AsyncStored.setItem('@RNAuth:user', JSON.stringify(response.user));
    await AsyncStored.setItem('@RNAuth:token', response.token);
  };

  const singOut = () => {
    AsyncStored.clear().then(() => setUser(null));
  };

  return (
    <AuthContext.Provider value={{signed: !!user, user, signIn, singOut}}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
