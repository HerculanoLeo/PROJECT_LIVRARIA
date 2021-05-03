import React from "react";
import { StyleSheet } from "react-native";

import { InputContainer, InputLabel, InputDescription, InputRow, InputErrorText } from "./styled";

interface LayoutFormInputProps {
  label: string;
  description?: string;
  isError?: object;
  textErrors?: string;
}

const LayoutFormInput: React.FC<LayoutFormInputProps> = ({
  label,
  description,
  children,
  isError,
  textErrors,
}) => {
  return (
    <InputContainer>
      <InputLabel>{label}{description && (<InputDescription>{` ${description}`}</InputDescription>)}</InputLabel>

      <InputRow style={styles.shadowInput} isError={isError ? true : false}>
        {children}
      </InputRow>
      {isError ? <InputErrorText>* {textErrors}</InputErrorText> : <></>}
    </InputContainer>
  );
};

export default LayoutFormInput;

const styles = StyleSheet.create({
  shadowInput: {
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 1,
    },
    shadowOpacity: 0.2,
    shadowRadius: 1.41,
    elevation: 2,
  },
});