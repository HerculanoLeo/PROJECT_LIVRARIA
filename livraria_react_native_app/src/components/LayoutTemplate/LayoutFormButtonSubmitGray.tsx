import React from "react";

import { SubmitButtonGray, TextButtonWhite } from "./styled";

interface LayoutFormButtonSubmitProps {
  label: string;
  onPress: () => void;
}

export const LayoutFormButtonSubmitGray: React.FC<LayoutFormButtonSubmitProps> = ({
  label,
  onPress,
}) => {
  return (
    <SubmitButtonGray onPress={onPress}>
      <TextButtonWhite>{label}</TextButtonWhite>
    </SubmitButtonGray>
  );
};