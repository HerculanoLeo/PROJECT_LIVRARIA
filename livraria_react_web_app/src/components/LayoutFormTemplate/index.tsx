import React from 'react';
import Messages from '../LayoutTemplate/Messages';

import { LayoutFormContainer, LayoutFormInputContainer, LayoutFormInputLabel, LayoutFormSubmitContainer, LayoutFormSubmitButtonsContainer } from './styled';

interface LayoutFormTemplateProps {
  onSubmit(): void;
}

interface LayoutFormInputProps {
  label: string;
  htmlFor: string;
}

interface LayoutFormSubmitProps {

}

export const LayoutFormTemplate: React.FC<LayoutFormTemplateProps> = ({ onSubmit, children }) => {
  return (
    <LayoutFormContainer name="form" onSubmit={onSubmit}>
      {children}
    </LayoutFormContainer>
  );
}

export const LayoutFormInput: React.FC<LayoutFormInputProps> = ({ label, htmlFor, children }) => {


  return (
    <LayoutFormInputContainer>
      <LayoutFormInputLabel htmlFor={htmlFor}>{label}</LayoutFormInputLabel>
      {children}
    </LayoutFormInputContainer>
  )
}

export const LayoutFormSubmit: React.FC<LayoutFormSubmitProps> = ({ children }) => {

  return (
    <LayoutFormSubmitContainer>
      <Messages />

      <LayoutFormSubmitButtonsContainer>
        {children}
      </LayoutFormSubmitButtonsContainer>
    </LayoutFormSubmitContainer>
  )
}