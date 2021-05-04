import React, { useState, useRef, useContext, useEffect } from "react";
import { useNavigation } from "@react-navigation/native";
import { useForm } from "react-hook-form";
import { faEyeSlash, faEye } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";
import * as yup from 'yup';
import { useDispatch } from "react-redux";

import { InputStyled, SubmitButtonContainer, PasswordHideButton } from "../../../../../components/LayoutTemplate/styled";
import { RegisterClientContext } from "../../../../../contexts/RegisterClientContext";
import { getLocale, } from "../../../../../utils/tools";
import UserService from "../../../../../services/UserService";
import LayoutFormButtonSubmitBlue from "../../../../../components/LayoutTemplate/LayoutFormButtonSubmitBlue";
import LayoutFormInput from "../../../../../components/LayoutTemplate/LayoutFormInput";
import LayoutFormTemplate from "../../../../../components/LayoutTemplate/LayoutFormTemplate";
import LayoutFormTitle from "../../../../../components/LayoutTemplate/LayoutFormTitle";
import { endLoading, startLoading } from "../../../../../redux/actions/Loading";


interface InputsProps {
  nome: string;
  documento: string;
  email: string;
  senha: string;
  confirmeSenha: string;
}

const AccessCreditials: React.FC = () => {

  const [hidePassword, setHidePassword] = useState(true);
  const [hideConfirmPassword, setHideConfirmPassword] = useState(true);
  const dispatchRedux = useDispatch();
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

    formSchema.validate(formData, { abortEarly: false }).then(async ({ email, senha, confirmeSenha }) => {
      try {
        dispatchRedux(startLoading({ isActivityIndicator: true, isBlockScreen: true }));

        dispatch({ type: 'accessCreditials', data: { email, senha, confirmeSenha } });

        await UserService.registerClient({ ...state, email, senha, confirmeSenha, idioma: getLocale() });

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
        });
      } catch (error) {
        navigation.reset({
          index: 1,
          routes: [
            {
              name: 'RegisterResult',
              params: {
                result: false,
                message: error.message
              }
            }
          ],
        });

      } finally {
        dispatchRedux(endLoading());
      }
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

export default AccessCreditials;