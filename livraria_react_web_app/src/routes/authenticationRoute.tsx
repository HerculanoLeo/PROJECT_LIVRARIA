import React, { Dispatch } from "react";
import { Route, Switch } from "react-router-dom";
import LoginPage from "../pages/Login";

const AuthenticationRoute: React.FC = () => {
  return (
    <Switch>
      <Route path="/" component={LoginPage} />
    </Switch>
  );
};

export default AuthenticationRoute;
