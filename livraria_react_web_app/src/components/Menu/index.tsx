import React, { useState } from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronDown, faChevronRight, faChevronUp, faHome, faSignOutAlt, faUser } from "@fortawesome/free-solid-svg-icons";

import MenuItem from "./MenuItem";
import SubMenu from "./SubMenu";

import { MenuMask, SidebarToogle, SidebarContainer, UserContainer, Avatar, User, UserButton, UserMenu, MenuStyled } from "./styled";

const urlImg = "https://image.freepik.com/fotos-gratis/paisagem-natural-em-um-jardim-botanico_35355-5948.jpg";

const Menu: React.FC = () => {
  const [isVisibleMenu, setVisibleMenu] = useState(false);
  const [isVisibleUsuarioMenu, setVisibleUsuarioMenu] = useState(false);

  return (
    <>
      <SidebarToogle isVisibleMenu={isVisibleMenu} onClick={() => setVisibleMenu(!isVisibleMenu)}>
        <FontAwesomeIcon icon={faChevronRight} size="sm" color="#000" />
      </SidebarToogle>
      <MenuMask isVisible={isVisibleMenu} onClick={() => setVisibleMenu(!isVisibleMenu)} />
      <SidebarContainer isVisible={isVisibleMenu}>
        <UserContainer>
          <Avatar img={urlImg} />

          <User>
            <UserButton onClick={() => setVisibleUsuarioMenu(!isVisibleUsuarioMenu)}>
              Nome do Usuário
              <FontAwesomeIcon icon={isVisibleUsuarioMenu ? faChevronUp : faChevronDown} size="sm" color="#000" />
            </UserButton>
          </User>

          <UserMenu isVisible={isVisibleUsuarioMenu}>
            <MenuItem to="/conta" icon={faUser} value="Conta" />

            <MenuItem to="/" icon={faSignOutAlt} value="Sair" />
          </UserMenu>
        </UserContainer>

        <MenuStyled>
          <MenuItem to="/" icon={faHome} value="Home" />

          <SubMenu value="Biblioteca">
            <MenuItem to="/" icon={faHome} value="Home" />
          </SubMenu>
        </MenuStyled>
      </SidebarContainer>
    </>
  );
};

export default Menu;
