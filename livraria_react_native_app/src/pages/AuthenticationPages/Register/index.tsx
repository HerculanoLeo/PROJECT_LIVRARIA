import React from 'react';
import { } from 'react-native';

import { useNavigation } from '@react-navigation/core';

import { LayoutFormButtonSubmitBlue, LayoutFormTemplate, LayoutFormTitle } from '../../../compenents/LayoutFormTemplate';
import { SubmitButtonContainer } from '../../../compenents/LayoutFormTemplate/styled';

const RegisterPage: React.FC = () => {

  const navigation = useNavigation();

  return (
    <LayoutFormTemplate>
      <LayoutFormTitle title="Como você gostaria de usar nosso App? " />

      <SubmitButtonContainer>
        <LayoutFormButtonSubmitBlue label={"Cliente"} onPress={() => { navigation.navigate('ClientRegisterScreens') }} />
      </SubmitButtonContainer>

      <SubmitButtonContainer>
        <LayoutFormButtonSubmitBlue label={"Biblioteca"} onPress={() => { navigation.navigate('LibraryRegisterScreens') }} />
      </SubmitButtonContainer>

    </LayoutFormTemplate>
  );
}

export default RegisterPage;