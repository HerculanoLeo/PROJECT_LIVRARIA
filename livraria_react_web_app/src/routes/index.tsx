import React from "react";

import { connect } from "react-redux";
import { BrowserRouter } from "react-router-dom";

import User from "../interfaces/User";
import { ApplicationState } from "../redux/reducers";
import AuthenticatedRoutes from "./authenticatedRoute";
import AuthenticationRoute from "./authenticationRoute";

interface indexRouteProps {
  expireToken: Date;
  usuario: User;
  isLoading: boolean;
}

const Routes: React.FC<indexRouteProps> = ({ usuario, expireToken, isLoading }) => {

  const isAutheticated = () => {
    const now = new Date();

    return (usuario && expireToken) ? expireToken > now : false;
  };


  return (
    <>
      {isLoading ? <></> : (
        <BrowserRouter>
          {isAutheticated() ? <AuthenticatedRoutes /> : <AuthenticationRoute />}
        </BrowserRouter>
      )}
    </>
  )
};

const mapStateToProps = ({ authentication }: ApplicationState): indexRouteProps => ({
  usuario: authentication.usuario,
  expireToken: authentication.expireToken,
  // isLoading: stored.isLoading,
  isLoading: false,
});

export default connect(mapStateToProps)(Routes);
