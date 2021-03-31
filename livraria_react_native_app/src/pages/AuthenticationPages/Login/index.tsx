import React, { useEffect, useState } from 'react';
import { View } from 'react-native';

import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import CheckBox from '@react-native-community/checkbox';
import { useNavigation } from '@react-navigation/core';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';

import {
  ForgotPasswordButton,
  ForgotPasswordButtonText,
  LoginCardContainer,
  Logo,
  LogoContainer,
  RememberMeContainer,
  CheckboxContainer,
  RememberMeText,
  RegisterButtonContainer,
} from './styled';
import { useDispatch } from 'react-redux';
import { LayoutFormButtonSubmitBlue, LayoutFormButtonSubmitGray, LayoutFormInput, LayoutFormTemplate } from '../../../compenents/LayoutFormTemplate';
import { InputStyled, PasswordHideButton, SubmitButtonContainer } from '../../../compenents/LayoutFormTemplate/styled';
import { loginRequest } from '../../../redux/actions/Login';


interface InputsProps {
  email: string;
  senha: string;
}

const LoginPage: React.FC = () => {
  const {
    register,
    setValue,
    handleSubmit,
    errors,
    setError,
  } = useForm<InputsProps>();

  const dispatch = useDispatch();

  const [hidePassword, setHidePassword] = useState(true);
  const [toggleCheckbox, setToggleCheckbox] = useState(false);

  const navigation = useNavigation();

  const formSchema = yup.object().shape({
    email: yup.string().required('E-mail é obrigatório'),
    senha: yup.string().required('Senha é obrigatório.')
  });

  useEffect(() => {
    register('email');
    register('senha');
  }, [register]);

  const submit = (data: InputsProps) => {
    formSchema.validate(data, { abortEarly: false }).then(({ email, senha }) => {
      dispatch(loginRequest(email, senha, toggleCheckbox));
    }).catch((errors) => {
      errors.inner.forEach((err: any) => {
        setError(err.path, { message: err.message });
      });
    });
  }

  return (
    <LayoutFormTemplate>
      <LoginCardContainer>
        <LogoContainer>
          <Logo source={require('../../../assest/bookImages.png')} />
        </LogoContainer>

        <LayoutFormInput label={'E-mail'} isError={errors.email} textErrors={errors.email?.message}>
          <InputStyled
            keyboardType="email-address"
            onChangeText={value => setValue('email', value)}
          />
        </LayoutFormInput>

        <LayoutFormInput label={'Senha'} isError={errors.senha} textErrors={errors.senha?.message}>
          <InputStyled
            keyboardType="ascii-capable"
            secureTextEntry={hidePassword}
            onChangeText={text => setValue('senha', text)}
          />

          <PasswordHideButton onPress={() => setHidePassword(!hidePassword)}>
            <FontAwesomeIcon
              icon={hidePassword ? faEyeSlash : faEye}
              size={24}
              color="#8a7676"
            />
          </PasswordHideButton>
        </LayoutFormInput>

        <RememberMeContainer>
          <CheckboxContainer>
            <CheckBox
              onAnimationType="fill"
              offAnimationType="fill"
              tintColors={{ true: '#000000', false: '#202020' }}
              tintColor="#000"
              onFillColor="#FFF"
              boxType="square"
              value={toggleCheckbox}
              onValueChange={setToggleCheckbox}
            />
            <RememberMeText>Lembre Me</RememberMeText>
          </CheckboxContainer>

          <ForgotPasswordButton onPress={() => { navigation.navigate('ForgotPasswordRoute') }}>
            <ForgotPasswordButtonText>Esqueceu a senha?</ForgotPasswordButtonText>
          </ForgotPasswordButton>
        </RememberMeContainer>

        <SubmitButtonContainer>
          <LayoutFormButtonSubmitBlue
            label="Entrar"
            onPress={handleSubmit(submit)}
          />

          <RegisterButtonContainer>
            <LayoutFormButtonSubmitGray label={'Registre-se'} onPress={() => navigation.navigate('RegisterRoute')} />
          </RegisterButtonContainer>
        </SubmitButtonContainer>
      </LoginCardContainer>
    </LayoutFormTemplate>
  );
};

export default LoginPage;
