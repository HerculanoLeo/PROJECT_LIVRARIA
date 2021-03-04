import styled from "styled-components";

interface TopbarLogoProps {
  img?: string;
}

export const TopbarContainer = styled.div`
  position: fixed;
  z-index: 1;
  display: flex;
  justify-content: center;
  align-items: stretch;

  width: 100%;
  height: 55px;

  background-color: #6062ec;

  -webkit-box-shadow: 0px 0px 50px 0px rgba(0, 0, 0, 0.2);
  box-shadow: 0px 0px 50px 0px rgba(0, 0, 0, 0.2);
`;

export const TopbarLogo = styled.div<TopbarLogoProps>`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 220px;

  background-color: #ffffff;
  background-image: ${(props) => `url(${props.img})`};
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
`;
