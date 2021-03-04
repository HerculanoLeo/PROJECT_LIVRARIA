import React from "react";
import { useHistory } from "react-router-dom";

import BookItem from "../../../components/BookItem";

import "../../../styles/pages/home.css";

const img = "https://cdn.shopify.com/s/files/1/0155/7645/products/html-css-featured_large.png?v=1411489770";

const Livros: React.FC = () => {
  const history = useHistory();

  const livros = [
    {
      title: "HTML e CSS",
      img: img,
      id: 1,
    },
    {
      title: "JAVA",
      img: img,
      id: 2,
    },
    {
      title: "REACT",
      img: img,
      id: 3,
    },
  ];

  const abrirDetalhes = (path: number) => {
    history.push(`/livros/${path}`);
  };

  return (
    <div className="home-container">
      <div className="card-container">
        {livros.map((livro, index) => {
          return (
            <BookItem
              key={index}
              title={livro.title}
              img={livro.img}
              buttonFunction={() => {
                abrirDetalhes(livro.id);
              }}
            />
          );
        })}
      </div>
    </div>
  );
};

export default Livros;
