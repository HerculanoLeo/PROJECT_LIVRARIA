import React, { useContext, useEffect } from 'react';
import { useNavigation } from '@react-navigation/core';
import { useForm } from 'react-hook-form';
import { useDispatch } from 'react-redux';
import * as yup from 'yup'

import UserService from '../../../../services/UserService';
import { errorMessage, warnMessage } from '../../../../redux/actions/Message';
import { InputStyled, SubmitButtonContainer, TextButtonBlack } from '../../../../components/LayoutTemplate/styled';
import { ForgotPasswordContext } from '../../../../contexts/ForgotPasswordContext';
import { HasCodeButton } from '../styled';
import LayoutFormButtonSubmitBlue from '../../../../components/LayoutTemplate/LayoutFormButtonSubmitBlue';
import LayoutFormDescription from '../../../../components/LayoutTemplate/LayoutFormDescription';
import LayoutFormInput from '../../../../components/LayoutTemplate/LayoutFormInput';
import LayoutFormTemplate from '../../../../components/LayoutTemplate/LayoutFormTemplate';
import LayoutFormTitle from '../../../../components/LayoutTemplate/LayoutFormTitle';
import { endLoading, startLoading } from '../../../../redux/actions/Loading';

interface InputProps {
  code: string;
}

const ValidateCodePage: React.FC = () => {
  const { register, handleSubmit, setValue, setError, errors } = useForm<InputProps>();
  const dispatchRedux = useDispatch();
  const navigation = useNavigation();

  const { state, dispatch } = useContext(ForgotPasswordContext);

  const formSchema = yup.object().shape({
    code: yup.string().matches(/^[0-9]*$/, 'Código Invalido.').required('Informe seu código')
  })

  useEffect(() => {
    register('code');
  }, [register]);


  const submit = (formData: InputProps) => {
    formSchema.validate(formData).then(async ({ code }) => {
      try {
        dispatchRedux(startLoading({ isActivityIndicator: true, isBlockScreen: false }));

        const { nome, email, dataValidade } = await UserService.validateResetPasswordCode({ code, email: state.email });

        dispatch({ type: 'code', data: { code, nome, email, dataValidade: new Date(dataValidade) } });

        if (new Date(dataValidade) > new Date()) {
          navigation.navigate('ResetPasswordPage');
        } else {
          dispatchRedux(warnMessage('Código expirado solicite um novo.'));
        }
      } catch (error) {
        dispatchRedux(errorMessage(error.message));
      } finally {
        dispatchRedux(endLoading());
      }
    }).catch((err) => {
      setError('code', err);
    })
  }

  return (
    <LayoutFormTemplate>
      <LayoutFormTitle title={'Verificação'} />

      <LayoutFormDescription description={'Confira em sua caixa de e-mail, acabamos de enviar o seu código.'} />

      <LayoutFormInput label={'Código'} isError={errors.code} textErrors={errors.code?.message}>
        <InputStyled
          keyboardType="number-pad"
          onChangeText={value => setValue('code', value)}
        />
      </LayoutFormInput>

      <SubmitButtonContainer>
        <LayoutFormButtonSubmitBlue label={'Validar'} onPress={handleSubmit(submit)} />

        <HasCodeButton onPress={() => UserService.changePassword({ email: state.email })}>
          <TextButtonBlack>Solicete um novo código</TextButtonBlack>
        </HasCodeButton>
      </SubmitButtonContainer>
    </LayoutFormTemplate>
  );
}

export default ValidateCodePage;