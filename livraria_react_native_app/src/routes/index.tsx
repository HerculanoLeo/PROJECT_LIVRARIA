import React, { useEffect } from 'react';
import { connect, useDispatch } from 'react-redux';
import { NavigationContainer } from '@react-navigation/native';
import {
  CardStyleInterpolators,
  createStackNavigator,
} from '@react-navigation/stack';
import SplashScreen from 'react-native-splash-screen';

import AuthenticationRoute from './authenticationRoute';
import AuthenticatedRoute from './authenticatedRoute';
import { ApplicationState } from '../redux/reducers';
import User from '../interfaces/User';
import { loadStored } from '../redux/actions/Stored';
import LayoutActivityIndicator from '../components/LayoutTemplate/LayoutActivityIndicator';

interface IndexRouteProps {
  expireToken: Date;
  usuario: User;
  isLoadingStored: boolean;
}

const Stack = createStackNavigator();

const Routes: React.FC<IndexRouteProps> = ({ usuario, expireToken, isLoadingStored }) => {
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(loadStored());
  }, [])

  useEffect(() => {
    if (!isLoadingStored) {
      SplashScreen.hide();
    }
  }, [isLoadingStored]);

  const isAutheticated = () => {
    const now = new Date();

    // return true;
    return (usuario && expireToken) ? expireToken > now : false;
  };

  return (
    <>
      <LayoutActivityIndicator />
      {isLoadingStored ? <></> : (
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
  isLoadingStored: stored.isLoadingStored
});

export default connect(mapPropsToState)(Routes);
