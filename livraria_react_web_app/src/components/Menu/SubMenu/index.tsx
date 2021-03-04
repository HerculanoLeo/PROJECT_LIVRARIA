import React, { ReactNode, useState } from "react";

import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

import { Submenu, SubmenuTitle, SubmenuValue, SubmenuIcon, SubmenuItems } from "./styled";

interface SubMenuProps {
  children?: ReactNode;
  value: string;
}

const SubMenu: React.FC<SubMenuProps> = (props: SubMenuProps) => {
  const [isVisibleSubMenu, setVisibleSubMenu] = useState(false);

  return (
    <Submenu isVisibleSubMenu={isVisibleSubMenu}>
      <SubmenuTitle onClick={() => setVisibleSubMenu(!isVisibleSubMenu)}>
        <SubmenuValue>{props.value}</SubmenuValue>
        <SubmenuIcon isVisibleSubMenu={isVisibleSubMenu}>
          <FontAwesomeIcon icon={faChevronDown} size="sm" color="#000" />
        </SubmenuIcon>
      </SubmenuTitle>
      <SubmenuItems>{props.children}</SubmenuItems>
    </Submenu>
  );
};

export default SubMenu;
