import React from "react";

import { FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { MenuIcon } from "../MenuItem/styled";
import { MenuButtonStyled } from "./styled";


interface MenuItemProps {
  onClick: () => void;
  value: string;
  icon: IconProp;
  rendered?: Boolean;
}

const MenuButton: React.FC<MenuItemProps> = (props: MenuItemProps) => {
  if (props.rendered === false) {
    return <></>;
  } else {
    return (
      <MenuButtonStyled onClick={props.onClick}>
          <MenuIcon>
            <FontAwesomeIcon icon={props.icon} size="sm" />
          </MenuIcon>
          {props.value}
      </MenuButtonStyled>
    );
  }
};

export default MenuButton;
