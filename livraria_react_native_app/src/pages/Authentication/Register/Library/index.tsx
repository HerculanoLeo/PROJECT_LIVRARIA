import React from 'react';
import { CardStyleInterpolators, createStackNavigator } from '@react-navigation/stack';

import RegisterLibraryContextProvader from '../../../../contexts/RegisterLibraryContext';
import LibraryInformation from './pages/LibraryInformation';
import AdministratorInformation from './pages/AdministratorInformation';
import AccessCreditials from './pages/AccessCreditials';
import RegisterResult from './pages/RegisterResult';

const Stack = createStackNavigator();

const LibraryRegisterScreens: React.FC = () => {
  return (
    <RegisterLibraryContextProvader>
      <Stack.Navigator
        screenOptions={{
          cardStyleInterpolator: CardStyleInterpolators.forHorizontalIOS,
          headerShown: false,
        }}>

        <Stack.Screen
          options={{ headerShown: true, headerTitle: '' }}
          name="LibraryInformation"
          component={LibraryInformation}
        />

        <Stack.Screen
          options={{ headerShown: true, headerTitle: '' }}
          name="AdministratorInformation"
          component={AdministratorInformation}
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
    </RegisterLibraryContextProvader>
  )
}

export default LibraryRegisterScreens;