import styled from "styled-components";

interface BookPhotoProps {
  img: string;
}

export const BookCard = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: stretch;

  -webkit-box-shadow: 0px 0px 20px 4px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 0px 20px 4px rgba(0, 0, 0, 0.3);

  width: 90%;
  height: 400px;

  border-radius: 6px;

  margin: 15px;
`;

export const BookPhoto = styled.div<BookPhotoProps>`
  align-self: center;

  width: 90%;
  height: 60%;

  margin-top: 25px;

  border-radius: 6px;

  background-color: #f1eaea8a;
  background-repeat: no-repeat;
  background-position: center center;
  background-size: contain;

  background-image: ${(props) => `url(${props.img})`};
`;

export const BookDetails = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: stretch;

  text-align: center;

  & h1 {
    margin: 15px 0;
  }

  & button {
    height: 30px;

    border: none;
    border-radius: 4px;

    margin: 10px 20px 0 20px;
    background-color: #6062ec;
    color: #ffffff;

    cursor: pointer;
    outline: none;
  }

  & button::active {
    opacity: 0.7;
    transition: opacity 0.3s;
  }
`;
