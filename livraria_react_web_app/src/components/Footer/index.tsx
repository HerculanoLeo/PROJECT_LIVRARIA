import React from "react";

import "../../styles/components/footer.css";

const Footer: React.FC = () => {
  return (
    <div className="footer-container">
      <div className="footer-author">
        <label>Autor: Herculano Dias</label>
      </div>
      <div className="footer-rights-reserved">
        <label>All Rights Reserved</label>
      </div>
    </div>
  );
};

export default Footer;
