import React from 'react';

import {
  CardStyleInterpolators,
  createStackNavigator,
} from '@react-navigation/stack';

import ForgotPasswordContextProvaider from '../contexts/ForgotPasswordContext';
import RegisterPage from '../pages/AuthenticationPages/Register';
import ClientRegisterScreens from '../pages/AuthenticationPages/Register/Client';
import LibraryRegisterScreens from '../pages/AuthenticationPages/Register/Library';

const RegisterRoute: React.FC = () => {
  const Stack = createStackNavigator();

  return (
    <Stack.Navigator
      screenOptions={{
        cardStyleInterpolator: CardStyleInterpolators.forHorizontalIOS,
      }}>

      <Stack.Screen
        options={{ headerShown: true, headerTitle: '' }}
        name="RegisterPage"
        component={RegisterPage}
      />

      <Stack.Screen
        options={{ headerShown: false, headerTitle: '' }}
        name="ClientRegisterScreens"
        component={ClientRegisterScreens}
      />

      <Stack.Screen
        options={{ headerShown: false, headerTitle: '' }}
        name="LibraryRegisterScreens"
        component={LibraryRegisterScreens}
      />

    </Stack.Navigator>
  );
};

export default RegisterRoute;