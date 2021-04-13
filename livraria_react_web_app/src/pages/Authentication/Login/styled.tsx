import styled from "styled-components";

interface LoginLogoProps {
  img: string;
}

export const LoginContainer = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100vw;
  height: 100vh;
  background-color: #e2eafc;
`;

export const LoginCard = styled.div`
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: center;
  width: 450px;
  min-height: 350px;
  background-color: #fff;
  border-radius: 4px;
  -webkit-box-shadow: 0px 0px 5px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 0px 5px 2px rgba(0, 0, 0, 0.3);
  @media (max-width: 640px) {
    width: 95%;
  }
`;

export const LoginLogoContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  height: 80px;
  margin: 10px 0 0 0;
`;

export const LoginLogo = styled.div<LoginLogoProps>`
  width: 140px;
  height: 75px;

  border-radius: 20px;

  background-image: ${(props) => `url(${props.img})`};
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
`;

export const LoginTitle = styled.h1`
  text-align: center;
  margin-top: 0;
  margin-bottom: 15px;
`;

export const LoginForm = styled.form`
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  align-content: center;
`;

export const LoginInputContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: flex-start;

  margin: 5px 30px;

  border-bottom: 1px solid #6b6b6b;
`;

export const LoginLabel = styled.label`
  font-weight: bold;
  margin: 0 10px;
`;

export const LoginInput = styled.input`
  padding: 5px;
  margin-top: 5px;

  border: 0;

  outline: none;

  &:-webkit-autofill {
    box-shadow: 0 0 0 30px white inset;
  }
`;

export const LoginButtonContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  margin: 5px 30px;

  text-align: end;
`;

export const LoginButton = styled.button`
  width: 120px;
  height: 25px;

  font-weight: bold;

  background-color: #c1d3fe;
  border-radius: 4px;
  border: none;

  -webkit-box-shadow: 0px 0px 5px 2px rgba(0, 0, 0, 0.1);
  box-shadow: 0px 0px 5px 2px rgba(0, 0, 0, 0.1);
`;
