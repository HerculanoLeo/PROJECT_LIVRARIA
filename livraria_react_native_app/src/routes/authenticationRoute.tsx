import React, { useEffect } from 'react';

import { useDispatch } from 'react-redux';
import { useNavigation } from '@react-navigation/core';
import {
  CardStyleInterpolators,
  createStackNavigator,
} from '@react-navigation/stack';

import LoginPage from '../pages/AuthenticationPages/Login';
import ForgotPasswordRoute from './forgotPasswordRoute';
import { cleanMessage } from '../redux/actions/Message';
import store from '../redux/store';
import RegisterRoute from './registerRoute';

const AuthenticationRoute: React.FC = () => {
  const Stack = createStackNavigator();

  const navigation = useNavigation();
  const dispatch = useDispatch();

  useEffect(() => {
    const unsubiscribe = navigation.addListener('state', ({ data }) => {
      if (store.getState().message.message && !store.getState().message.isPersistenceMessage) {
        dispatch(cleanMessage());
      }
    });

    return unsubiscribe;
  }, []);

  return (
    <Stack.Navigator
      screenOptions={{
        cardStyleInterpolator: CardStyleInterpolators.forHorizontalIOS,
      }}>

      <Stack.Screen
        options={{ headerShown: false, headerTitle: '' }}
        name="LoginPage"
        component={LoginPage}
      />

      <Stack.Screen
        options={{ headerShown: false }}
        name="ForgotPasswordRoute"
        component={ForgotPasswordRoute}
      />

      <Stack.Screen
        options={{ headerShown: false }}
        name="RegisterRoute"
        component={RegisterRoute}
      />

    </Stack.Navigator>
  );
};

export default AuthenticationRoute;
