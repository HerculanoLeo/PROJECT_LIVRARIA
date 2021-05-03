import styled from 'styled-components/native';
import { TextInputMask } from 'react-native-masked-text';

interface InputRowProps {
  isError?: boolean;
}

interface MessageCardProps {
  color: string;
}

export const LayoutContainer = styled.SafeAreaView`
  flex: 1;
  background-color: #e2eafc;
`;

export const LayoutScrollViewContainer = styled.ScrollView`
  flex: 1;
  flex-direction: column;
  padding: 0px 12px 0 12px;
`;

export const LayoutContentContainer = styled.KeyboardAvoidingView`
  flex: 1;
  margin-bottom: 20px;
`;

export const FormTitleContainer = styled.View`
  flex-direction: row;
  align-items: flex-start;
  justify-content: space-between;
  margin: 15px 0 10px 0;
`;

export const FormTitle = styled.Text`
  font-size: 26px;
  font-weight: bold;
`;

export const FormDescriptionContainer = styled.View`
  flex-direction: row;
  align-items: flex-start;
  justify-content: space-between;
  margin: 2px 5px 2px 5px;
`;

export const FormDescription = styled.Text`
  font-size: 18px;
  text-align: left;
`;

export const FormSectionInfoContainer = styled.View`
  margin-top: 20px;
  border-bottom-width: 1px;
`;

export const FormSectionInfo = styled.Text`
  font-size: 15px;
  font-weight: bold;
`;

export const InputContainer = styled.View`
  flex-direction: column;
  margin: 16px 4px 0 4px;
`;

export const InputLabel = styled.Text`
  color: #000;
  font-weight: bold;
  font-size: 16px;
  margin-left: 6px;
`;

export const InputDescription = styled.Text`
  color: #000;
  font-weight: 400;
  font-size: 12px;
  margin-left: 8px;
`;

export const InputStyled = styled.TextInput`
  flex: 1;
  padding: 10px 20px;
  font-size: 14px;
  color: #000;
`;

export const InputMaskStyled = styled(TextInputMask)`
  flex: 1;
  padding: 10px 20px;
  font-size: 14px;
  color: #000;
`;

export const InputRow = styled.View<InputRowProps>`
  flex-direction: row;
  align-items: stretch;
  justify-content: space-between;
  border: ${props => (props.isError ? `2px` : `0px`)};
  border-color: ${props => (props.isError ? `#ff0000` : `rgba(52, 52, 52, 1)`)};
  border-radius: 4px;
  background-color: #fff;
  margin: 2px 2px 0 2px;
`;

export const InputErrorText = styled.Text`
  margin-left: 4px;
  color: #ff0000;
`;

export const PasswordHideButton = styled.TouchableOpacity`
  align-items: center;
  justify-content: center;
  width: 50px;
`;

export const SubmitButtonContainer = styled.View`
  flex-direction: column;
  align-items: stretch;
  justify-content: center;
  margin-top: 35px;
`;

export const SubmitButtonBlue = styled.TouchableOpacity`
  background-color: #6670fc;
  height: 50px;
  justify-content: center;
  border-radius: 4px;
`;

export const SubmitButtonGray = styled.TouchableOpacity`
  background-color: #5b5b61;
  height: 50px;
  justify-content: center;
  border-radius: 4px;
`;

export const TextButtonWhite = styled.Text`
  font-size: 18px;
  font-weight: bold;
  color: #fff;
  text-align: center;
`;

export const TextButtonBlack = styled.Text`
  font-size: 18px;
  font-weight: bold;
  color: #000;
  text-align: center;
`;

export const MessageContainer = styled.View`
  flex: 1;
  position: absolute;
  top: 40px;
  z-index: 1;
  min-height: 60px;
  width: 100%;
  flex-direction: column;
  align-items: stretch;
  justify-content: center;
  padding: 0 12px;
  border-radius: 4px;
`;

export const MessageCard = styled.View<MessageCardProps>`
  flex: 1;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  padding: 5px 10px 5px 5px;
  border-radius: 4px;
  background-color: ${props => `${props.color}`};
`;

export const TextMessage = styled.Text`
  flex: 1;
  color: #000000;
  font-size: 14px;
  text-align: center;
`;
