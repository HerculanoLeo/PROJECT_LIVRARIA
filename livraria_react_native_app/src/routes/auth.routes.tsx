import React from 'react'
import {  } from 'react-native';
import { createStackNavigator } from "@react-navigation/stack";

import SingIn from '../pages/SingIn';

const AuthStack = createStackNavigator();

const AuthRoute: React.FC = () => {

    return (
        <AuthStack.Navigator>
            <AuthStack.Screen name="SingIn" component={SingIn} options={{ 
                headerShown: false,
                cardStyle: {
                    backgroundColor: '#DFF8FF',
                }
                }}/>
        </AuthStack.Navigator>
    )
}

export default AuthRoute;

