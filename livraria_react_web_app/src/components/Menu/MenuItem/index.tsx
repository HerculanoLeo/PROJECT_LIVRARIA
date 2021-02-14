import React from "react";

import { FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { Link } from "react-router-dom";

import "../../../styles/components/Menu/menuitem.css";

interface MenuItemProps {
  to: string;
  value: string;
  icon: IconProp;
  rendered?: Boolean;
}

const MenuItem: React.FC<MenuItemProps> = (props: MenuItemProps) => {
  if (null != props.rendered && props.rendered === false) {
    return <></>;
  } else {
    return (
      <div className="menu-item">
        <Link to={props.to}>
          <div className="div-svg">
            <FontAwesomeIcon icon={props.icon} size="sm" />
          </div>
          {props.value}
        </Link>
      </div>
    );
  }
};

export default MenuItem;
