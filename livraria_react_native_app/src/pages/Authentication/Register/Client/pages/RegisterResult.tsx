import React, { useContext } from "react";
import { useNavigation, useRoute } from "@react-navigation/native";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";
import { faCheckCircle, faTimesCircle } from "@fortawesome/free-solid-svg-icons";

import { SubmitButtonContainer } from "../../../../../components/LayoutTemplate/styled";
import { RegisterClientContext } from "../../../../../contexts/RegisterClientContext";
import { useDispatch } from "react-redux";
import { loginRequest } from "../../../../../redux/actions/Login";
import { IconRegisterResultContainer, RegisterMessageContainer, RegisterMessageText } from "../styled";
import LayoutFormButtonSubmitBlue from "../../../../../components/LayoutTemplate/LayoutFormButtonSubmitBlue";
import LayoutFormTemplate from "../../../../../components/LayoutTemplate/LayoutFormTemplate";


interface ParamsProps {
  result: boolean,
  message: string
}

const RegisterResult: React.FC = () => {
  const { state, dispatch } = useContext(RegisterClientContext);

  const { result, message }: ParamsProps = useRoute().params as ParamsProps;

  const navigation = useNavigation();

  const dispatchRedux = useDispatch();

  return (
    <LayoutFormTemplate>
      <IconRegisterResultContainer>
        <FontAwesomeIcon
          icon={result ? faCheckCircle : faTimesCircle}
          size={120}
          color={result ? "#09ff00" : "#FF0000"}
          secondaryColor={"#000"} />
      </IconRegisterResultContainer>

      <RegisterMessageContainer>
        <RegisterMessageText>{message}</RegisterMessageText>
      </RegisterMessageContainer>

      <SubmitButtonContainer>
        <LayoutFormButtonSubmitBlue label={result ? 'Entre' : 'Tente Novamente'} onPress={() => {
          if (result) {
            dispatchRedux(loginRequest(state.email, state.senha, true));
          } else {
            dispatch({ type: 'init' });

            navigation.reset({
              index: 1,
              routes: [{ name: 'ClientInformation' }]
            })
          }
        }} />
      </SubmitButtonContainer>

    </LayoutFormTemplate>
  )
}

export default RegisterResult;