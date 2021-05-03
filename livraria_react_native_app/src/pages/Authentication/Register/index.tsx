import React from 'react';
import { useNavigation } from '@react-navigation/core';

import { SubmitButtonContainer } from '../../../components/LayoutTemplate/styled';
import LayoutFormButtonSubmitBlue from '../../../components/LayoutTemplate/LayoutFormButtonSubmitBlue';
import LayoutFormTemplate from '../../../components/LayoutTemplate/LayoutFormTemplate';
import LayoutFormTitle from '../../../components/LayoutTemplate/LayoutFormTitle';

const Register: React.FC = () => {

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

export default Register;