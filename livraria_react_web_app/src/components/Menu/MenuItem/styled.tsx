import styled from "styled-components";

export const MenuItemStyled = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;

  & a {
    display: flex;
    width: 100%;
    color: #000000;
  }

  & a:link {
    text-decoration: none;
  }

  & a:visited {
    text-decoration: none;
  }
`;

export const MenuIcon = styled.div`
  margin: 0 0px 0 25px;
  height: 24px;
  width: 24px;
`;
