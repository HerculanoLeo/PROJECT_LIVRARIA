import React from "react";

import { Link } from "react-router-dom";

import "../../styles/menu.css";

const Menu: React.FC = () => {
  return (
    <div className="container">
      <div className="logo">
        {/* <img src="https://image.freepik.com/fotos-gratis/paisagem-natural-em-um-jardim-botanico_35355-5948.jpg" /> */}
      </div>
      <div className="menu">
        <div className="menu-item">
          <Link to="/">Home</Link>
        </div>
        <div>
          <Link to="/">Home</Link>
        </div>
      </div>
    </div>
  );
};

export default Menu;
