import React, { useState, useRef, useContext, useEffect } from "react";
import { useNavigation } from "@react-navigation/native";
import { useForm } from "react-hook-form";
import * as yup from 'yup';

import { InputStyled, InputMaskStyled, SubmitButtonContainer } from "../../../../../components/LayoutTemplate/styled";
import {RegisterLibraryContext} from "../../../../../contexts/RegisterLibraryContext";
import { validateCnpj, validateCPF } from "../../../../../utils/validators";
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

const AdministratorInformation: React.FC = () => {

  const [documento, setDocumento] = useState('');
  const [typeFavoredCompany, setTypeFavoredCompany] = useState('cpf');

  const documentoRef = useRef<any>(null);
  const { dispatch } = useContext(RegisterLibraryContext);

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
    documento: yup.string().required('CFP/CNPJ é obrigatório.')
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
        dispatch({ type: 'administratorInformation', data: { nome, documento: documentoRef.current.getRawValue() } });

        navigation.navigate('AccessCreditials')
      } else {
        setError('documento', { message: 'CFP/CNPJ Inválido.' })
      }

    }).catch((errors) => {
      errors.inner.forEach((err: any) => {
        setError(err.path, { message: err.message });
      });
    });
  }

  return (
    <LayoutFormTemplate>
      <LayoutFormTitle title="Preencha com os dados do Administrador" />

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

export default AdministratorInformation;