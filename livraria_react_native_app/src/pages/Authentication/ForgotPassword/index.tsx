import React, { useContext, useEffect } from 'react';
import { useNavigation } from '@react-navigation/core';
import { useForm } from 'react-hook-form';
import { useDispatch } from 'react-redux';
import * as yup from 'yup';

import { InputStyled, SubmitButtonContainer } from '../../../components/LayoutTemplate/styled';
import { ForgotPasswordContext } from '../../../contexts/ForgotPasswordContext';
import { errorMessage } from '../../../redux/actions/Message';
import UserService from '../../../services/UserService';
import LayoutFormButtonSubmitBlue from '../../../components/LayoutTemplate/LayoutFormButtonSubmitBlue';
import LayoutFormDescription from '../../../components/LayoutTemplate/LayoutFormDescription';
import LayoutFormInput from '../../../components/LayoutTemplate/LayoutFormInput';
import LayoutFormTemplate from '../../../components/LayoutTemplate/LayoutFormTemplate';
import LayoutFormTitle from '../../../components/LayoutTemplate/LayoutFormTitle';
import { endLoading, startLoading } from '../../../redux/actions/Loading';

interface InputProps {
  email: string
}

const ForgotPasswordPage: React.FC = () => {
  const { register, handleSubmit, setValue, setError, errors } = useForm<InputProps>();
  const dispatchRedux = useDispatch();
  const navigation = useNavigation();

  const { state, dispatch } = useContext(ForgotPasswordContext);

  const formSchema = yup.object().shape({
    email: yup.string().email('Preencha com um e-mail valído.').required('Informe seu e-mail')
  });

  useEffect(() => {
    register('email');
  }, [register]);

  const submit = (formData: InputProps) => {
    formSchema.validate(formData).then(async ({ email }) => {
      try {
        dispatchRedux(startLoading({ isActivityIndicator: true, isBlockScreen: false }));

        await UserService.changePassword({ email });

        dispatch({ type: 'email', email })

        navigation.navigate('ValidateCodePage');
      } catch (error) {
        dispatchRedux(errorMessage(error.message, false));
      } finally {
        dispatchRedux(endLoading());
      }
    }).catch((err) => {
      setError('email', err);
    })
  }

  return (
    <LayoutFormTemplate>
      <LayoutFormTitle title={'Esqueceu à senha?'} />

      <LayoutFormDescription description={'Informa o E-mail associado a sua conta'} />

      <LayoutFormInput label={'E-mail'} isError={errors.email} textErrors={errors.email?.message}>
        <InputStyled
          keyboardType="email-address"
          onChangeText={value => setValue('email', value)}
        />
      </LayoutFormInput>

      <SubmitButtonContainer>
        <LayoutFormButtonSubmitBlue label={'Enviar'} onPress={handleSubmit(submit)} />
      </SubmitButtonContainer>
    </LayoutFormTemplate>
  );
}

export default ForgotPasswordPage;