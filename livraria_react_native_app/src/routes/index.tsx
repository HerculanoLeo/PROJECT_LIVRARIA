import React, { useEffect } from 'react';

import { connect, useDispatch } from 'react-redux';
import { NavigationContainer } from '@react-navigation/native';
import {
  CardStyleInterpolators,
  createStackNavigator,
} from '@react-navigation/stack';

import AuthenticationRoute from './authenticationRoute';
import AuthenticatedRoute from './authenticatedRoute';
import { ApplicationState } from '../redux/reducers/index';
import User from '../interfaces/User';
import { loadStored } from '../redux/actions/Stored';
import SplashScreen from 'react-native-splash-screen';

interface IndexRouteProps {
  expireToken: Date;
  usuario: User;
  isLoading: boolean;
}

const Routes: React.FC<IndexRouteProps> = ({ usuario, expireToken, isLoading }) => {
  const Stack = createStackNavigator();

  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(loadStored());
  }, [])

  useEffect(() => {
    if (!isLoading) {
      SplashScreen.hide();
    }
  }, [isLoading]);

  const isAutheticated = () => {
    const now = new Date();

    // return true;
    return (usuario && expireToken) ? expireToken > now : false;
  };

  return (
    <>
      {isLoading ? <></> : (
        <NavigationContainer>
          <Stack.Navigator
            screenOptions={{ cardStyleInterpolator: CardStyleInterpolators.forHorizontalIOS }}>
            {isAutheticated() ? (
              <Stack.Screen
                options={{ headerShown: false }}
                name="AuthenticatedRoute"
                component={AuthenticatedRoute}
              />
            ) : (
              <Stack.Screen
                options={{ headerShown: false }}
                name="AuthenticationRoute"
                component={AuthenticationRoute}
              />
            )}
          </Stack.Navigator>
        </NavigationContainer>
      )}
    </>
  );
};

const mapPropsToState = ({ authentication, stored }: ApplicationState): IndexRouteProps => ({
  usuario: authentication.usuario,
  expireToken: authentication.expireToken,
  isLoading: stored.isLoading
});

export default connect(mapPropsToState)(Routes);
