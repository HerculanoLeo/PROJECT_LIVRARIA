import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Footer from "../components/Footer";
import Menu from "../components/Menu";
import Topbar from "../components/Topbar";

import Conta from "../pages/Conta";
import Home from "../pages/Home";

const Routes: React.FC = () => {
  return (
    <BrowserRouter>
      <div className="layout-container">
        <Topbar />
        <Menu />
        <div className="content-container">
          <div className="content">
            <Switch>
              <Route path="/" exact component={Home} />
              <Route path="/conta" component={Conta} />
            </Switch>
          </div>
          <Footer />
        </div>
      </div>
    </BrowserRouter>
  );
};

export default Routes;
