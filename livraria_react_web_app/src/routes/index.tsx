import React from "react";

import { connect } from "react-redux";

import { BrowserRouter } from "react-router-dom";
import { AuthenticationUserState } from "../interfaces/User/authenticationUserState";
import { ApplicationState } from "../redux/reducers";

import BibliotecaRoutes from "../routes/bibliotecaBibliotecaRoute";
import AuthenticationRoute from "./authenticationRoute";

const Routes: React.FC<AuthenticationUserState> = ({ autentication }) => {
  const isAutheticated = (): Boolean => {
    const now = new Date();

    if (autentication && autentication.expireToken.getMilliseconds() < now.getMilliseconds()) {
      return true;
    } else {
      return false;
    }
  };

  return <BrowserRouter>{isAutheticated() ? <BibliotecaRoutes /> : <AuthenticationRoute />}</BrowserRouter>;
};

const mapStateToProps = ({ authentication }: ApplicationState): AuthenticationUserState => ({
  ...authentication,
});

export default connect(mapStateToProps)(Routes);
