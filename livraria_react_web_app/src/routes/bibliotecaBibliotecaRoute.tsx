import React from "react";
import { Route, Switch } from "react-router-dom";

import Livros from "../pages/Cliente/Livros";
import LayoutTemplate from "../components/LayoutTemplate";

const BibliotecaRoutes: React.FC = () => {
  return (
    <LayoutTemplate>
      <Switch>
        <Route path="/" component={Livros} />
      </Switch>
    </LayoutTemplate>
  );
};

export default BibliotecaRoutes;
