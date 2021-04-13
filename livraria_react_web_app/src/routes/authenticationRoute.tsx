import React from "react";
import { Redirect, Route, Switch } from "react-router-dom";

import LoginPage from "../pages/Authentication/Login";

const AuthenticationRoute: React.FC = () => {
  return (
    <Switch >
      <Route path="/login" component={LoginPage} />
      
      <Route>
        <Redirect to="/login" />
      </Route>
    </Switch>
  );
};

export default AuthenticationRoute;
