import React from "react";

import { FormTitleContainer, FormTitle } from "./styled";

interface LayoutFormTitleProps {
  title: string;
}

const LayoutFormTitle: React.FC<LayoutFormTitleProps> = ({ title }) => {
  return (
    <FormTitleContainer>
      <FormTitle>{title}</FormTitle>
    </FormTitleContainer>
  );
};

export default LayoutFormTitle;