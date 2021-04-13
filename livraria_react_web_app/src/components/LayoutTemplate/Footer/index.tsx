import React from "react";

import { FooterContainer, FooterAuthor, FooterRightReserved } from "./styled";

const Footer: React.FC = () => {
  return (
    <FooterContainer>
      <FooterAuthor>
        <label>Autor: Herculano Dias</label>
      </FooterAuthor>
      <FooterRightReserved>
        <label>All Rights Reserved</label>
      </FooterRightReserved>
    </FooterContainer>
  );
};

export default Footer;
