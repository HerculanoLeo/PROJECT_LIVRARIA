import React, { Dispatch } from "react";

import { connect, useDispatch } from "react-redux";
import { Link } from "react-router-dom";
import { useForm } from "react-hook-form";

import { AuthenticationUserState } from "../../../interfaces/User";
import { loginRequest } from "../../../redux/actions/Login";
import { ApplicationState } from "../../../redux/reducers";
import {
  LoginContainer,
  LoginTitle,
  LoginCard,
  LoginForm,
  LoginInputContainer,
  LoginLabel,
  LoginInput,
  LoginButtonContainer,
  LoginButton,
  LoginLogoContainer,
  LoginLogo,
} from "./styled";
import { logoImg } from "../../../assest/logo";
import Messages from "../../../components/LayoutTemplate/Messages";
import { LayoutFormInput, LayoutFormSubmit, LayoutFormTemplate } from "../../../components/LayoutFormTemplate";
import { LayoutFormInputStyled, LayoutFormSubmitButtonsContainer } from "../../../components/LayoutFormTemplate/styled";

interface InputsProps {
  email: string;
  password: string;
}

const LoginPage: React.FC<AuthenticationUserState> = ({ }) => {
  const { register, handleSubmit, watch, errors } = useForm<InputsProps>();

  const dispatch: Dispatch<any> = useDispatch();

  const formSubmit = async (data: InputsProps) => {
    const { email, password } = data;

    if (email && password) {
      dispatch(loginRequest(email, password));
    }
  };

  return (
    <LoginContainer>
      <LoginCard>
        <LoginLogoContainer>
          <LoginLogo img={logoImg} />
        </LoginLogoContainer>

        <LoginTitle>Acesso</LoginTitle>

        <LayoutFormTemplate onSubmit={handleSubmit(formSubmit)}>
          <LayoutFormInput htmlFor="email" label="E-mail">
            <LayoutFormInputStyled type="text" className="form-control" name="email" defaultValue={""} ref={register({ required: true })} />
          </LayoutFormInput>

          <LayoutFormInput htmlFor="password" label="Senha">
            <LayoutFormInputStyled type="password" className="form-control" name="password" defaultValue={""} ref={register({ required: true })} />
          </LayoutFormInput>

          <LayoutFormSubmit>
            <LoginButton>Entrar</LoginButton>

            <Link to="/reset-password">Esqueceu à senha?</Link>
          </LayoutFormSubmit>
        </LayoutFormTemplate>
      </LoginCard>
    </LoginContainer>
  );
};

const mapStateToProps = ({ authentication }: ApplicationState): AuthenticationUserState => ({
  ...authentication,
});

export default connect(mapStateToProps)(LoginPage);
