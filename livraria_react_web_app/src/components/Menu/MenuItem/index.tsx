import React from "react";

import { FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { Link } from "react-router-dom";

import { MenuItemStyled, MenuIcon } from './styled';

interface MenuItemProps {
  to: string;
  value: string;
  icon: IconProp;
  rendered?: Boolean;
}

const MenuItem: React.FC<MenuItemProps> = (props: MenuItemProps) => {
  if (props.rendered === false) {
    return <></>;
  } else {
    return (
      <MenuItemStyled>
        <Link to={props.to}>
          <MenuIcon>
            <FontAwesomeIcon icon={props.icon} size="sm" />
          </MenuIcon>
          {props.value}
        </Link>
      </MenuItemStyled>
    );
  }
};

export default MenuItem;
