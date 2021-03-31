import 'react-native-gesture-handler';
import React from 'react';
import {StatusBar} from 'react-native';

import {Provider} from 'react-redux';
import {SafeAreaProvider} from 'react-native-safe-area-context';

import Routes from './src/routes';
import store from './src/redux/store';
import {BASE_URL, ENV} from '@env';
import { LayoutFormMassage } from './src/compenents/LayoutFormTemplate';

const App = () => {
  console.log(ENV, BASE_URL);

  return (
    <Provider store={store}>
      <SafeAreaProvider>
        <StatusBar barStyle="light-content" />
        <LayoutFormMassage />
        <Routes />
      </SafeAreaProvider>
    </Provider>
  );
};

export default App;
