import React from 'react';
import {
  CardStyleInterpolators,
  createStackNavigator,
} from '@react-navigation/stack';

import Register from '../pages/Authentication/Register';
import ClientRegisterScreens from '../pages/Authentication/Register/Client';
import LibraryRegisterScreens from '../pages/Authentication/Register/Library';

const Stack = createStackNavigator();

const RegisterRoute: React.FC = () => {
  return (
    <Stack.Navigator
      screenOptions={{
        cardStyleInterpolator: CardStyleInterpolators.forHorizontalIOS,
      }}>
      <Stack.Screen
        options={{ headerShown: true, headerTitle: '' }}
        name="Register"
        component={Register}
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