import React, { useContext, useEffect, useState } from 'react';
import * as yup from 'yup';
import { useDispatch } from 'react-redux';
import { useForm } from 'react-hook-form';
import { useNavigation } from '@react-navigation/core';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';

import { InputStyled, PasswordHideButton, SubmitButtonContainer } from '../../../../components/LayoutTemplate/styled';
import { ForgotPasswordContext } from '../../../../contexts/ForgotPasswordContext';
import { errorMessage, successMessage } from '../../../../redux/actions/Message';
import UserService from '../../../../services/UserService';
import LayoutFormButtonSubmitBlue from '../../../../components/LayoutTemplate/LayoutFormButtonSubmitBlue';
import LayoutFormInput from '../../../../components/LayoutTemplate/LayoutFormInput';
import LayoutFormTemplate from '../../../../components/LayoutTemplate/LayoutFormTemplate';
import LayoutFormTitle from '../../../../components/LayoutTemplate/LayoutFormTitle';
import { endLoading, startLoading } from '../../../../redux/actions/Loading';

interface InputProps {
  senha: string;
  confirmaSenha: string;
}

const ResetPasswordPage: React.FC = () => {
  const [hidePassword, setHidePassword] = useState(true);
  const [hideConfirmePassword, setHideConfirmePassword] = useState(true);

  const { register, handleSubmit, setValue, setError, errors, clearErrors } = useForm<InputProps>();
  const dispatchRedux = useDispatch();
  const navigation = useNavigation();

  const { state } = useContext(ForgotPasswordContext);

  const formSchema = yup.object().shape({
    senha: yup.string().min(4, 'Senha deve ter ao menos 4 caracteres.').required('Senha não pode estar vazio.'),
    confirmaSenha: yup.string().oneOf([yup.ref('senha'), null], 'Confirma Senha deve ser igual a Senha.').required('Confirme Senha não pode estar vazio.')
  });

  useEffect(() => {
    const unsubscribe = navigation.addListener('focus', () => {
      console.warn('blur');

      clearErrors();
    });

    return unsubscribe;
  }, []);

  useEffect(() => {
    register('senha');
    register('confirmaSenha');
  }, [register]);


  const submit = (formData: InputProps) => {
    formSchema.validate(formData, { abortEarly: false }).then(async ({ senha, confirmaSenha }) => {
      try {
        dispatchRedux(startLoading({ isActivityIndicator: true, isBlockScreen: false }));

        await UserService.resetPasswordWithCode({ code: state.code, email: state.email, senha, confirmaSenha });

        dispatchRedux(successMessage('Senha trocada com sucesso.', true));

        navigation.navigate('LoginPage');
      } catch (error) {
        dispatchRedux(errorMessage(error.message));
      } finally {
        dispatchRedux(endLoading());
      }
    }).catch((errors) => {
      errors.inner.forEach((err: any) => {
        setError(err.path, { message: err.message });
      });
    })
  }

  return (
    <LayoutFormTemplate>
      <LayoutFormTitle title={'Crie sua nova senha'} />

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

      <LayoutFormInput label={'Confirma Senha'} isError={errors.confirmaSenha} textErrors={errors.confirmaSenha?.message}>
        <InputStyled
          keyboardType="ascii-capable"
          secureTextEntry={hideConfirmePassword}
          onChangeText={text => setValue('confirmaSenha', text)}
        />

        <PasswordHideButton onPress={() => setHideConfirmePassword(!hideConfirmePassword)}>
          <FontAwesomeIcon
            icon={hideConfirmePassword ? faEyeSlash : faEye}
            size={24}
            color="#8a7676"
          />
        </PasswordHideButton>
      </LayoutFormInput>

      <SubmitButtonContainer>
        <LayoutFormButtonSubmitBlue label={'Salvar'} onPress={handleSubmit(submit)} />
      </SubmitButtonContainer>
    </LayoutFormTemplate>
  );
}

export default ResetPasswordPage;