import React from 'react';
import { CardStyleInterpolators, createStackNavigator } from '@react-navigation/stack';

import RegisterClientContenxtProvaider from '../../../../contexts/RegisterClientContext';
import ClientInformation from './pages/ClientInformation';
import AccessCreditials from '../Library/pages/AccessCreditials';
import RegisterResult from './pages/RegisterResult';

const Stack = createStackNavigator();

const ClientRegisterScreens: React.FC = () => {

  return (
    <RegisterClientContenxtProvaider>
      <Stack.Navigator
        screenOptions={{
          cardStyleInterpolator: CardStyleInterpolators.forHorizontalIOS,
          headerShown: false,
        }}>

        <Stack.Screen
          options={{ headerShown: true, headerTitle: '' }}
          name="ClientInformation"
          component={ClientInformation}
        />

        <Stack.Screen
          options={{ headerShown: true, headerTitle: '' }}
          name="AccessCreditials"
          component={AccessCreditials}
        />

        <Stack.Screen
          options={{ headerShown: false, headerTitle: '' }}
          name="RegisterResult"
          component={RegisterResult}
        />

      </Stack.Navigator>
    </RegisterClientContenxtProvaider>
  )
}

export default ClientRegisterScreens;