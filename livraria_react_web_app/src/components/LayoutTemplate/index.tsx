import React from "react";
import Topbar from "../Topbar";
import { Content, ContentConatiner, LayoutConainer } from "./styled";

import Menu from '../Menu/index'


const LayoutTemplate: React.FC = ({ children }) => {
  return (
    <LayoutConainer>
      <Topbar />
      <Menu />
      <ContentConatiner>
        <Content>{children}</Content>
      </ContentConatiner>
    </LayoutConainer>
  );
};

export default LayoutTemplate;
