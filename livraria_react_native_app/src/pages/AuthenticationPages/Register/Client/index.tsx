import React, { useContext, useEffect, useRef, useState } from 'react';
import { } from 'react-native';

import * as yup from 'yup';
import { useNavigation, useRoute } from '@react-navigation/core';
import { CardStyleInterpolators, createStackNavigator } from '@react-navigation/stack';
import { useForm } from 'react-hook-form';
import { faCheckCircle, faEye, faEyeSlash, faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';

import RegisterClientContenxtProvaider, { RegisterClientContext } from '../../../../contexts/RegisterClientContext';
import { LayoutFormButtonSubmitBlue, LayoutFormInput, LayoutFormTemplate, LayoutFormTitle } from '../../../../compenents/LayoutFormTemplate';
import { InputMaskStyled, InputStyled, PasswordHideButton, SubmitButtonContainer } from '../../../../compenents/LayoutFormTemplate/styled';
import { getLocale, validateCnpj, validateCPF } from '../../../../utils';
import { IconRegisterResultContainer, RegisterMessageContainer, RegisterMessageText } from './styled';
import UserService from '../../../../services/UserService';
import { useDispatch } from 'react-redux';
import { loginRequest } from '../../../../redux/actions/Login';

interface InputsProps {
  nome: string;
  documento: string;
  email: string;
  senha: string;
  confirmeSenha: string;
}

const ClientInformation: React.FC = () => {

  const [documento, setDocumento] = useState('');
  const [typeFavoredCompany, setTypeFavoredCompany] = useState('cpf');

  const documentoRef = useRef<any>(null);
  const { dispatch } = useContext(RegisterClientContext);

  const {
    register,
    setValue,
    handleSubmit,
    errors,
    setError
  } = useForm<InputsProps>();


  const navigation = useNavigation();

  const formSchema = yup.object().shape({
    nome: yup.string().required('Nome é obrigatório'),
    documento: yup.string().required('CPF/CNPJ é obrigatório.')
  });

  useEffect(() => {
    register('nome');
    register('documento');
  }, [register]);

  const submit = (data: InputsProps) => {

    const formData: InputsProps = {
      ...data,
      nome: data.nome,
      documento
    }

    formSchema.validate(formData, { abortEarly: false }).then(({ nome, documento }) => {

      if (documentoRef.current.isValid()) {
        dispatch({ type: 'personalInformation', data: { nome, documento: documentoRef.current.getRawValue() } });

        navigation.navigate('AccessCreditials')
      } else {
        setError('documento', { message: 'Documento Inválido.' })
      }

    }).catch((errors) => {
      errors.inner.forEach((err: any) => {
        setError(err.path, { message: err.message });
      });
    });
  }

  return (
    <LayoutFormTemplate>
      <LayoutFormTitle title="Preencha com seus dados Pessoais" />

      <LayoutFormInput label="Nome" isError={errors.nome} textErrors={errors.nome?.message} >
        <InputStyled onChangeText={(value) => setValue('nome', value)} />
      </LayoutFormInput>

      <LayoutFormInput label="CPF ou CNPJ" isError={errors.documento} textErrors={errors.documento?.message} >
        <InputMaskStyled
          type="custom"
          keyboardType="numeric"
          ref={documentoRef}
          options={{
            mask: typeFavoredCompany === 'cpf' ? '999.999.999-99*' : '99.999.999/9999-99',
            validator: (value: string) => {
              return validateCnpj(value) || validateCPF(value);
            },
            getRawValue: (value) => {
              return (value || '').replace(/\.|\/|-/g, '');
            },
          }}
          value={documento}
          onChangeText={(text) => {
            setDocumento(text);
            setTypeFavoredCompany(text.length > 14 ? 'cnpj' : 'cpf');
          }} />
      </LayoutFormInput>

      <SubmitButtonContainer>
        <LayoutFormButtonSubmitBlue label="Proxímo" onPress={handleSubmit(submit)} />
      </SubmitButtonContainer>

    </LayoutFormTemplate>
  )
}

const AccessCreditials: React.FC = () => {

  const [hidePassword, setHidePassword] = useState(true);
  const [hideConfirmPassword, setHideConfirmPassword] = useState(true);

  const { state, dispatch } = useContext(RegisterClientContext);

  const {
    register,
    setValue,
    handleSubmit,
    errors,
    setError
  } = useForm<InputsProps>();

  const navigation = useNavigation();

  const formSchema = yup.object().shape({
    email: yup.string()
      .email('Preencha com um E-mail valido.')
      .required('E-mail é obrigatório'),
    senha: yup.string()
      .matches(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{6,}$/, `A senha deve conter ao menos 6 caracteres, dentre eles, 1 letra Maiuscula, 1 minuscula, 1 número e 1 caracter especial.`)
      .required('Senha é obrigatório'),
    confirmeSenha: yup.string()
      .oneOf([yup.ref('senha'), null], 'Confirme Senha deve ser igual á Senha.')
      .required('Confirme Senha é obrigatório.')
  });

  useEffect(() => {
    register('email');
    register('senha');
    register('confirmeSenha');
  }, [register]);

  const submit = (data: InputsProps) => {

    const formData: InputsProps = {
      ...data
    }

    formSchema.validate(formData, { abortEarly: false }).then(({ email, senha, confirmeSenha }) => {
      dispatch({ type: 'accessCreditials', data: { email, senha, confirmeSenha } });

      UserService.registerClient({ ...state, email, senha, confirmeSenha, idioma: getLocale() }).then((value) => {
        navigation.reset({
          index: 1,
          routes: [
            {
              name: 'RegisterResult',
              params: {
                result: true,
                message: 'Cadastro realizado com sucesso!'
              }
            }
          ],
        })


      }).catch((err) => {
        navigation.reset({
          index: 1,
          routes: [
            {
              name: 'RegisterResult',
              params: {
                result: false,
                message: err.message
              }
            }
          ],
        });
      })
    }).catch((errors) => {
      errors.inner.forEach((err: any) => {
        setError(err.path, { message: err.message });
      });
    });
  }

  return (
    <LayoutFormTemplate>
      <LayoutFormTitle title="Preencha com os dados de Acesso" />

      <LayoutFormInput label="Email" isError={errors.email} textErrors={errors.email?.message} >
        <InputStyled onChangeText={(value) => setValue('email', value)} />
      </LayoutFormInput>

      <LayoutFormInput label="Senha" isError={errors.senha} textErrors={errors.senha?.message} >
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

      <LayoutFormInput label="Confirme Senha" isError={errors.confirmeSenha} textErrors={errors.confirmeSenha?.message} >
        <InputStyled
          keyboardType="ascii-capable"
          secureTextEntry={hideConfirmPassword}
          onChangeText={text => setValue('confirmeSenha', text)}
        />

        <PasswordHideButton onPress={() => setHideConfirmPassword(!hideConfirmPassword)}>
          <FontAwesomeIcon
            icon={hideConfirmPassword ? faEyeSlash : faEye}
            size={24}
            color="#8a7676"
          />
        </PasswordHideButton>
      </LayoutFormInput>

      <SubmitButtonContainer>
        <LayoutFormButtonSubmitBlue label="Proxímo" onPress={handleSubmit(submit)} />
      </SubmitButtonContainer>
    </LayoutFormTemplate>
  )
}

interface ParamsProps {
  result: boolean,
  message: string
}

const RegisterResult: React.FC = () => {
  const { state, dispatch } = useContext(RegisterClientContext);

  const { result, message }: ParamsProps = useRoute().params as ParamsProps;

  const navigation = useNavigation();

  const dispatchRedux = useDispatch();

  return (
    <LayoutFormTemplate>
      <IconRegisterResultContainer>
        <FontAwesomeIcon
          icon={result ? faCheckCircle : faTimesCircle}
          size={120}
          color={result ? "#09ff00" : "#FF0000"}
          secondaryColor={"#000"} />
      </IconRegisterResultContainer>

      <RegisterMessageContainer>
        <RegisterMessageText>{message}</RegisterMessageText>
      </RegisterMessageContainer>

      <SubmitButtonContainer>
        <LayoutFormButtonSubmitBlue label={result ? 'Entre' : 'Tente Novamente'} onPress={() => {
          if (result) {
            dispatchRedux(loginRequest(state.email, state.senha, true));
          } else {
            dispatch({ type: 'init' });

            navigation.reset({
              index: 1,
              routes: [{ name: 'ClientInformation' }]
            })
          }
        }} />
      </SubmitButtonContainer>

    </LayoutFormTemplate>
  )
}

const ClientRegisterScreens: React.FC = () => {
  const Stack = createStackNavigator();

  return (
    <RegisterClientContenxtProvaider>
      <Stack.Navigator
        screenOptions={{
          cardStyleInterpolator: CardStyleInterpolators.forHorizontalIOS,
          headerShown: false,
        }}>

        <Stack.Screen
          options={{ headerShown: true, headerTitle: '' }}
          name="ClientInformation"
          component={ClientInformation}
        />

        <Stack.Screen
          options={{ headerShown: true, headerTitle: '' }}
          name="AccessCreditials"
          component={AccessCreditials}
        />

        <Stack.Screen
          options={{ headerShown: false, headerTitle: '' }}
          name="RegisterResult"
          component={RegisterResult}
        />

      </Stack.Navigator>
    </RegisterClientContenxtProvaider>
  )
}

export default ClientRegisterScreens;