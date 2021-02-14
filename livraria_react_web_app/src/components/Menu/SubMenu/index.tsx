import React, { ReactNode, useState } from "react";

import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

import '../../../styles/components/Menu/submenu.css'

interface SubMenuProps {
  children?: ReactNode;
  value: string;
}

const SubMenu: React.FC<SubMenuProps> = (props: SubMenuProps) => {
  const [isVisibleSubMenu, setVisibleSubMenu] = useState(false);

  return (
    <div className={`submenu ${isVisibleSubMenu ? "submenu-expanded" : ""} `}>
      <div onClick={() => setVisibleSubMenu(!isVisibleSubMenu)} className="submenu-title">
        <span>{props.value}</span>
        <div className="submenu-icon">
          <FontAwesomeIcon icon={faChevronDown} size="sm" color="#000" />
        </div>
      </div>
      <div className="submenu-items">{props.children}</div>
    </div>
  );
};

export default SubMenu;
