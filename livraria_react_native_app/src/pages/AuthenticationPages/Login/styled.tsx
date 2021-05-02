import styled from 'styled-components/native';

export const LoginCardContainer = styled.View`
  flex: 1;
  flex-direction: column;
  align-items: stretch;
  justify-content: space-evenly;
`;

export const LogoContainer = styled.View`
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 150px;
  margin-top: 30px;
`;

export const Logo = styled.Image`
  height: 140px;
  width: 140px;
  border-radius: 16px;
`;

export const RememberMeContainer = styled.View`
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  margin: 5px 8px 0 0;
`;

export const CheckboxContainer = styled.View`
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  margin: 0 0 0 8px;
`;

export const RememberMeText = styled.Text`
  text-align: center;
  font-weight: bold;
  font-size: 14px;
  margin: 0 0 0 5px;
`;

export const ForgotPasswordButton = styled.TouchableOpacity`
  align-items: flex-end;
  justify-content: flex-start;
`;

export const ForgotPasswordButtonText = styled.Text`
  text-align: center;
  font-weight: bold;
  font-size: 14px;
`;

export const RegisterButtonContainer = styled.View`
  flex-direction: column;
  align-items: stretch;
  justify-content: center;
  border-top-width: 1px;
  border-top-color: #000;
  margin-top: 10px;
  padding: 10px 0 0 0;
`;
