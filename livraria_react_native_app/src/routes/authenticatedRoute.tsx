import React, { Dispatch, useEffect } from 'react';

import {CardStyleInterpolators, createStackNavigator} from '@react-navigation/stack';
import { useDispatch } from 'react-redux';
import { useNavigation } from '@react-navigation/core';

import { cleanMessage } from '../redux/actions/Message';
import DashboardPage from '../pages/AuthenticatedPages/Dashboard';
import store from '../redux/store';

const AuthenticatedRoute: React.FC = () => {
  const Stack = createStackNavigator();

  const navigation = useNavigation();
  const dispatch: Dispatch<any> = useDispatch();

  useEffect(() => {
    const unsubiscribe = navigation.addListener('state', ({ data }) => {
      if (store.getState().message.message && !store.getState().message.isPersistenceMessage) {
        dispatch(cleanMessage());
      }
    });

    return unsubiscribe;
  }, []);

  return (
    <Stack.Navigator screenOptions={{cardStyleInterpolator: CardStyleInterpolators.forHorizontalIOS}}>
      <Stack.Screen
        options={{headerShown: false}}
        name="DashboardPage"
        component={DashboardPage}
      />
    </Stack.Navigator>
  );
};

export default AuthenticatedRoute;
