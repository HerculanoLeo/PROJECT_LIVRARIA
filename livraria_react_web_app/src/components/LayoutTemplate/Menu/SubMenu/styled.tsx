import styled from "styled-components";

interface SubmenuProps {
  isVisibleSubMenu: boolean;
}

interface SubmenuTitleProps {
  onClick: (isVisibleSubMenu: boolean) => void;
}

export const Submenu = styled.div<SubmenuProps>`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: stretch;

  max-height: ${(props) => (props.isVisibleSubMenu ? "500px" : "24px")};

  overflow: hidden;
  transition: max-height 0.5s;
`;

export const SubmenuTitle = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: stretch;
  cursor: pointer;
  margin: 0 25px;
`;

export const SubmenuValue = styled.span``;

export const SubmenuIcon = styled.div<SubmenuProps>`
  transform: ${(props) => (props.isVisibleSubMenu ? "rotate(-180deg)" : "rotate(0deg)")};
  transition: transform 0.3s;
`;

export const SubmenuItems = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: stretch;

  margin-left: 10px;
  margin-top: 3px;
`;
