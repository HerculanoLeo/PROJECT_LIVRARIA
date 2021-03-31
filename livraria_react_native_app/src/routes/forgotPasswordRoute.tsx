import React from 'react';

import {
  CardStyleInterpolators,
  createStackNavigator,
} from '@react-navigation/stack';

import ForgotPasswordPage from '../pages/AuthenticationPages/ForgotPassword';
import ValidateCodePage from '../pages/AuthenticationPages/ForgotPassword/ValidateCode';
import ResetPasswordPage from '../pages/AuthenticationPages/ForgotPassword/ResetPassword';
import ForgotPasswordContextProvaider from '../contexts/ForgotPasswordContext';

const ForgotPasswordRoute: React.FC = () => {
  const Stack = createStackNavigator();

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