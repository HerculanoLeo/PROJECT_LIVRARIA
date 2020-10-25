import 'react-native-gesture-handler';

import React from 'react';
import {NavigationContainer} from '@react-navigation/native';

import Routes from './routes';
import {AuthProvaider} from './contexts/auth';

const App = () => {
  return (
    <NavigationContainer>
      <AuthProvaider>
        <Routes />
      </AuthProvaider>
    </NavigationContainer>
  );
};

export default App;
