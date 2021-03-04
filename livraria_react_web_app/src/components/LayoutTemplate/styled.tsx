import styled from "styled-components";

export const LayoutConainer = styled.div`
  display: flex;
  flex-direction: column;

  height: 100vh;
`;

export const ContentConatiner = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: stretch;
  align-items: stretch;

  height: 100%;

  margin-top: 55px;
`;

export const Content = styled.div`
  flex: 1;
`;
