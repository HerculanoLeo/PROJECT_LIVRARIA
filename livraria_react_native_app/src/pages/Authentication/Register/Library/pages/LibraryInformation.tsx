import React, { useContext, useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigation } from "@react-navigation/native";
import * as yup from 'yup';

import { InputStyled, SubmitButtonContainer } from "../../../../../components/LayoutTemplate/styled";
import { RegisterLibraryContext } from "../../../../../contexts/RegisterLibraryContext";
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

const LibraryInformation: React.FC = () => {

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
    nomeBiblioteca: yup.string().required('Nome da Biblioteca é obrigatório')
  });

  useEffect(() => {
    register('nomeBiblioteca');
  }, [register]);

  const submit = (data: InputsProps) => {

    const formData: InputsProps = {
      ...data,
    }

    formSchema.validate(formData, { abortEarly: false }).then(({ nomeBiblioteca }) => {
      dispatch({ type: 'libraryInformation', data: { nomeBiblioteca } });

      navigation.navigate('AdministratorInformation')
    }).catch((errors) => {
      errors.inner.forEach((err: any) => {
        setError(err.path, { message: err.message });
      });
    });
  }

  return (
    <LayoutFormTemplate>
      <LayoutFormTitle title="Preencha com dados da Biblioteca" />

      <LayoutFormInput label="Nome da Biblioteca" isError={errors.nomeBiblioteca} textErrors={errors.nomeBiblioteca?.message} >
        <InputStyled onChangeText={(value) => setValue('nomeBiblioteca', value)} />
      </LayoutFormInput>

      <SubmitButtonContainer>
        <LayoutFormButtonSubmitBlue label="Proxímo" onPress={handleSubmit(submit)} />
      </SubmitButtonContainer>

    </LayoutFormTemplate>
  )
}

export default LibraryInformation;