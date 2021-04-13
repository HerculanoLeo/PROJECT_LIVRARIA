import styled from "styled-components";

export const FooterContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: stretch;

  height: 45px;

  -webkit-box-shadow: 0px -2px 50px -5px rgba(0, 0, 0, 0.2);
  box-shadow: 0px -2px 50px -5px rgba(0, 0, 0, 0.2);

  & > div {
    flex: 1;
  }
`;

export const FooterAuthor = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;

  margin-left: 5px;
`;

export const FooterRightReserved = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
  
  margin-right: 5px;
`;