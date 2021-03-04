import styled from "styled-components";
import { ContentConatiner } from "../LayoutTemplate/styled";


interface SidebarToogleProps {
  isVisibleMenu: boolean;
}

interface MenuMaskProps {
  isVisible: boolean;
}

interface SidebarContainerPorps {
  isVisible: boolean;
}

interface AvatarProps {
  img: string;
}

interface UserMenuProps {
  isVisible: boolean;
}

export const SidebarToogle = styled.div<SidebarToogleProps>`
  position: fixed;
  top: 8px;
  left: 230px;
  z-index: 2;
  display: flex;
  justify-content: center;
  align-items: center;

  height: 35px;
  width: 35px;
  border-radius: 18px;

  background-color: #ffffff;
  cursor: pointer;

  transform: ${(props) => (props.isVisibleMenu ? "rotate(-180deg)" : "rotate(0deg)")};
  transition: transform 0.5s;

  @media (max-width: 768px) {
    left: 10px;
  }
`;

export const MenuMask = styled.div<MenuMaskProps>`
  @media (max-width: 768px) {
    position: fixed;
    display: ${(props) => (props.isVisible ? "block" : "none")};

    height: 100%;
    width: 100%;
    z-index: 1;

    background-color: black;
    margin-top: 55px;
    opacity: 0.3;
  }
`;

export const SidebarContainer = styled.div<SidebarContainerPorps>`
  position: fixed;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: flex-start;

  margin-top: 55px;
  height: 100%;
  width: 250px;
  max-width: ${(props) => (props.isVisible ? "250px" : "0px")};

  background-color: #ffffff;

  -webkit-box-shadow: 0px 1px 15px 3px rgba(116, 116, 116, 0.16);
  box-shadow: 0px 1px 15px 3px rgba(116, 116, 116, 0.16);

  overflow: hidden;

  transition: max-width 0.5s;

  @media (max-width: 768px) {
    z-index: 2;
  }

  @media (min-width: 769px) {
    & + ${ContentConatiner} {
      margin-left: ${(props) => (props.isVisible ? "250px" : "0px")};
      transition: margin-left 0.5s;
    }
  }
`;

export const UserContainer = styled.div`
  width: 250px;
  padding: 20px 0 10px 0;
  border-top: 0;

  background-color: #ffffff;

  -webkit-box-shadow: 0px 1px 15px 3px rgba(116, 116, 116, 0.25);
  box-shadow: 0px 1px 15px 3px rgba(116, 116, 116, 0.25);
`;

export const Avatar = styled.div<AvatarProps>`
  height: 65px;
  width: 65px;

  border-radius: 33px;

  margin: auto;
  margin-bottom: 20px;

  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;

  background-image: ${(props) => `url(${props.img})`};
`;

export const User = styled.div`
  display: flex;
  align-items: stretch;
  justify-content: center;

  width: 250px;

  text-align: center;
  font-weight: bold;
  vertical-align: middle;
`;

export const UserButton = styled.button`
  background-color: #ffffff;

  border: 0;

  font-size: 16px;
  font-weight: bold;

  cursor: pointer;
  outline: none;

  & svg {
    margin-left: 5px;
  }
`;

export const UserMenu = styled.div<UserMenuProps>`
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: flex-start;
  overflow: hidden;

  margin-top: "10px";
  max-height: ${(props) => (props.isVisible ? "80px" : "0px")};

  transition: max-height 0.5s;
`;

export const MenuStyled = styled.div`
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: flex-start;

  height: 100%;

  padding-top: 10px;
`;
