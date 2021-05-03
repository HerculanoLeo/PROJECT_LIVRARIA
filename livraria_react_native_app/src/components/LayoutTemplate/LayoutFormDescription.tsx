import React from "react";

import { FormDescriptionContainer, FormDescription } from "./styled";

interface LayoutFormDescriptionProps {
  description: string;
}

const LayoutFormDescription: React.FC<LayoutFormDescriptionProps> = ({ description }) => {
  return (
    <FormDescriptionContainer>
      <FormDescription>{description}</FormDescription>
    </FormDescriptionContainer>
  );
};

export default LayoutFormDescription;