import React, { useState, useContext, useEffect } from "react";
import { useForm } from "react-hook-form";
import { faEyeSlash, faEye } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";
import { useNavigation } from "@react-navigation/native";
import * as yup from 'yup';

import { InputStyled, PasswordHideButton, SubmitButtonContainer } from "../../../../../components/LayoutTemplate/styled";
import {RegisterLibraryContext} from "../../../../../contexts/RegisterLibraryContext";
import LibraryService from "../../../../../services/LibraryService";
import { getLocale } from "../../../../../utils/tools";
import LayoutFormButtonSubmitBlue from "../../../../../components/LayoutTemplate/LayoutFormButtonSubmitBlue";
import LayoutFormInput from "../../../../../components/LayoutTemplate/LayoutFormInput";
import LayoutFormTemplate from "../../../../../components/LayoutTemplate/LayoutFormTemplate";
import LayoutFormTitle from "../../../../../components/LayoutTemplate/LayoutFormTitle";

interface InputsProps {
  nome: string;
  documento: string;
  email: string;
  senha: string;
  confirmeSenha: string;
  nomeBiblioteca: string;
}

const AccessCreditials: React.FC = () => {

  const [hidePassword, setHidePassword] = useState(true);
  const [hideConfirmPassword, setHideConfirmPassword] = useState(true);

  const { state, dispatch } = useContext(RegisterLibraryContext);

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

      LibraryService.registerLibraryWithAdministrator({ ...state, email, senha, confirmeSenha, idioma: getLocale() }).then((value) => {
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

export default AccessCreditials;