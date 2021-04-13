import React from "react";

import { TopbarContainer, TopbarLogo } from "./styled";

const img =
  "https://images.educamaisbrasil.com.br/content/banco_de_imagens/guia-de-estudo/D/uma-montanha-e-classifica-como-paisagem-natural-estatica.jpg";

const Topbar: React.FC = () => {
  return (
    <TopbarContainer>
      <TopbarLogo img={img} />
    </TopbarContainer>
  );
};

export default Topbar;
