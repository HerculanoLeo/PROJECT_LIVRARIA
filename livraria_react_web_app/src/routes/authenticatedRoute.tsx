import React from "react";
import { Route, Switch } from "react-router-dom";


import LayoutTemplate from "../components/LayoutTemplate";
import Livros from "../pages/Authenticated/Cliente/Livros";

const AuthenticatedRoutes: React.FC = () => {
  return (
    <LayoutTemplate>
      <Switch>
        <Route path="/" component={Livros} />
      </Switch>
    </LayoutTemplate>
  );
};

export default AuthenticatedRoutes;
