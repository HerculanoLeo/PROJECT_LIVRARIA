import React from 'react';
import {
  CardStyleInterpolators,
  createStackNavigator,
} from '@react-navigation/stack';

import ForgotPasswordPage from '../pages/Authentication/ForgotPassword';
import ValidateCodePage from '../pages/Authentication/ForgotPassword/ValidateCode';
import ResetPasswordPage from '../pages/Authentication/ForgotPassword/ResetPassword';
import ForgotPasswordContextProvaider from '../contexts/ForgotPasswordContext';

const Stack = createStackNavigator();

const ForgotPasswordRoute: React.FC = () => {
  return (
    <ForgotPasswordContextProvaider>
      <Stack.Navigator
        screenOptions={{
          cardStyleInterpolator: CardStyleInterpolators.forHorizontalIOS,
        }}>
        <Stack.Screen
          options={{ headerShown: true, headerTitle: '' }}
          name="ForgotPasswordPage"
          component={ForgotPasswordPage}
        />

        <Stack.Screen
          options={{ headerShown: true, headerTitle: '' }}
          name="ValidateCodePage"
          component={ValidateCodePage}
        />

        <Stack.Screen
          options={{ headerShown: true, headerTitle: '' }}
          name="ResetPasswordPage"
          component={ResetPasswordPage}
        />
      </Stack.Navigator>
    </ForgotPasswordContextProvaider>
  );
};

export default ForgotPasswordRoute;