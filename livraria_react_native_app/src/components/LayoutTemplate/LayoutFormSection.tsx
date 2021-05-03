import React from "react";

import { FormSectionInfoContainer, FormSectionInfo } from "./styled";

interface LayoutFormSectionProps {
  description: string;
}

const LayoutFormSection: React.FC<LayoutFormSectionProps> = ({ description }) => {
  return (
    <FormSectionInfoContainer>
      <FormSectionInfo>{description}</FormSectionInfo>
    </FormSectionInfoContainer>
  );
};

export default LayoutFormSection;