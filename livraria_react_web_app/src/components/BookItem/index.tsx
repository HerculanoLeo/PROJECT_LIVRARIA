import React from "react";
import { BookCard, BookDetails, BookPhoto } from "./styled";

interface LivroItemProps {
  img: string;
  title: string;
  buttonFunction: () => void;
}

const BookItem: React.FC<LivroItemProps> = (props: LivroItemProps) => {
  return (
    <BookCard>
      <BookPhoto img={props.img} />
      <BookDetails>
        <h1>{props.title}</h1>
        <button onClick={props.buttonFunction}>Abrir</button>
      </BookDetails>
    </BookCard>
  );
};

export default BookItem;
