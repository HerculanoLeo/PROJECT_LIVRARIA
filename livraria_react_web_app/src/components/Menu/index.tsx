import React, { useState } from "react";

import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronDown, faChevronLeft, faChevronRight, faChevronUp, faHome, faSignOutAlt, faUser } from "@fortawesome/free-solid-svg-icons";

import "../../styles/components/menu.css";

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
            <div className="menu-item">
              <Link to="/conta">
                <div className="div-svg">
                  <FontAwesomeIcon icon={faUser} size="sm" />
                </div>
                Conta
              </Link>
            </div>

            <div className="menu-item">
              <Link to="/">
                <div className="div-svg">
                  <FontAwesomeIcon icon={faSignOutAlt} size="sm" />
                </div>
                <span>Sair</span>
              </Link>
            </div>
          </div>
        </div>

        <div className="menu">
          <div className="menu-item">
            <Link to="/">
              <div className="div-svg">
                <FontAwesomeIcon icon={faHome} size="sm" />
              </div>
              Home
            </Link>
          </div>

          <div className="menu-item">
            <Link to="/">
              <div className="div-svg">
                <FontAwesomeIcon icon={faChevronUp} size="sm" />
              </div>
              Home
            </Link>
          </div>
        </div>
      </div>
    </>
  );
};

export default Menu;
