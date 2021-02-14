import React, { useState } from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronDown, faChevronRight, faChevronUp, faHome, faSignOutAlt, faUser } from "@fortawesome/free-solid-svg-icons";

import MenuItem from "./MenuItem";
import SubMenu from "./SubMenu";

import "../../styles/components/Menu/menu.css";

const urlImg = "https://image.freepik.com/fotos-gratis/paisagem-natural-em-um-jardim-botanico_35355-5948.jpg";

const Menu: React.FC = () => {
  
  const [isVisibleMenu, setVisibleMenu] = useState(false);
  const [isVisibleUsuarioMenu, setVisibleUsuarioMenu] = useState(false);

  return (
    <>
      <div onClick={() => setVisibleMenu(!isVisibleMenu)} className={`sidebar-toogle ${isVisibleMenu ? "" : "sidebar-toogle-contracted"}`}>
        <FontAwesomeIcon icon={faChevronRight} size="sm" color="#000" />
      </div>

      <div className={`menu-mask ${isVisibleMenu ? "menu-mask-visible" : ""}`} onClick={() => setVisibleMenu(!isVisibleMenu)} />

      <div id="sidebar" className={`sidebar-container ${isVisibleMenu ? "" : "sidebar-container-contracted"}`}>
        <div className="avatar">
          <div className="div-avatar" style={{ backgroundImage: `url(${urlImg})` }} />

          <div className="div-nome-usuario">
            <button onClick={() => setVisibleUsuarioMenu(!isVisibleUsuarioMenu)}>
              Nome do Usuário
              <FontAwesomeIcon icon={isVisibleUsuarioMenu ? faChevronUp : faChevronDown} size="sm" color="#000" />
            </button>
          </div>

          <div className={`menu-usuario ${isVisibleUsuarioMenu ? "menu-usuario-expanded" : "menu-usuario-contracted"}`}>
            <MenuItem to="/conta" icon={faUser} value="Conta" />

            <MenuItem to="/" icon={faSignOutAlt} value="Sair" />
          </div>
        </div>

        <div className="menu">
          <MenuItem to="/" icon={faHome} value="Home" />

          <SubMenu value="Biblioteca" >
              <MenuItem to="/conta" icon={faHome} value="Livros" />
              <MenuItem to="/" icon={faHome} value="Autores" />
          </SubMenu>

          <MenuItem to="/" icon={faHome} value="Pedidos" />
        </div>
      </div>
    </>
  );
};

export default Menu;
