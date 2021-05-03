import 'react-native-gesture-handler';
import React from 'react';
import { Platform, StatusBar } from 'react-native';
import { Provider } from 'react-redux';
import { SafeAreaProvider } from 'react-native-safe-area-context';

import Routes from './src/routes';
import store from './src/redux/store';
import { BASE_URL, ENV } from '@env';
import LayoutFormMassage from './src/components/LayoutTemplate/LayoutFormMassage';

const App: React.FC = () => {
  console.log(ENV, BASE_URL);

  return (
    <Provider store={store}>
      <SafeAreaProvider>
        <StatusBar barStyle={Platform.OS === 'ios' ? 'dark-content' : 'light-content'} />
        <LayoutFormMassage />
        <Routes />
      </SafeAreaProvider>
    </Provider>
  );
};

export default App;
