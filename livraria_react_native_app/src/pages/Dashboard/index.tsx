import React, { useContext } from 'react';
import { Text, View } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import AuthContext from '../../contexts/auth';

const Dashboard: React.FC = () => {

    const { singOut } = useContext(AuthContext)

    return(
        <View>
            <TouchableOpacity onPress={singOut} >
                <Text>Sing out</Text>
            </TouchableOpacity>
        </View>
    )
}

export default Dashboard;