import React, { Dispatch, FormEvent, useState } from "react";

import { connect, useDispatch } from "react-redux";
import { Link } from "react-router-dom";
import { useForm } from "react-hook-form";

import { AuthenticationUserState } from "../../interfaces/User/authenticationUserState";
import { loginRequest } from "../../redux/actions/Login/actionLogin";
import { ApplicationState } from "../../redux/reducers";
import { LoginContainer, LoginTitle, LoginCard, LoginForm, LoginInputContainer, LoginLabel, LoginInput, LoginButtonContainer, LoginButton, LoginLogoContainer, LoginLogo } from "./styled";
import { logoImg } from "../../images/logo";

interface Inputs {
  email: string;
  password: string;
}

const img: string = "";

const LoginPage: React.FC<AuthenticationUserState> = ({ loggingIn }) => {
  const { register, handleSubmit, watch, errors } = useForm<Inputs>();

  const dispatch: Dispatch<any> = useDispatch();

  const formSubmit = async (data: Inputs) => {
    const { email, password } = data;

    console.log(email, password);

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

        <LoginForm name="form" onSubmit={handleSubmit(formSubmit)}>
          <LoginInputContainer>
            <LoginLabel htmlFor="username">E-mail</LoginLabel>

            <LoginInput type="text" className="form-control" name="email" defaultValue={""} ref={register({ required: true })} />
          </LoginInputContainer>

          <LoginInputContainer>
            <LoginLabel htmlFor="password">Senha</LoginLabel>

            <LoginInput type="password" className="form-control" name="password" defaultValue={""} ref={register({ required: true })} />
          </LoginInputContainer>

          <LoginButtonContainer>
            <LoginButton>Entrar</LoginButton>

            <Link to="/reset-password">Esqueceu à senha?</Link>
          </LoginButtonContainer>
        </LoginForm>
      </LoginCard>
    </LoginContainer>
  );
};

const mapStateToProps = ({ authentication }: ApplicationState): AuthenticationUserState => ({
  ...authentication,
});

export default connect(mapStateToProps)(LoginPage);
