import styled from 'styled-components';

export const LayoutFormContainer = styled.form`
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: flex-start;
  padding: 0px 30px 15px 30px;
`;

export const LayoutFormInputContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: flex-start;
  margin: 2px 0 0 0;
  border-bottom: 1px solid #6b6b6b;
`;

export const LayoutFormInputLabel = styled.label`
  font-weight: bold;
  margin: 0 10px;
`;

export const LayoutFormInputStyled = styled.input`
  padding: 5px;
  border: 0;
  outline: none;
  &:-webkit-autofill {
    box-shadow: 0 0 0 30px white inset;
  }
`;

export const LayoutFormSubmitContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: flex-end;
  margin: 10px 0 0 0;
`;

export const LayoutFormSubmitButtonsContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  margin: 10px 0 0 0;
`;