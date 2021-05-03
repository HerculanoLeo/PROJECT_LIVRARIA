import React from "react";

import { SubmitButtonBlue, TextButtonWhite } from "./styled";

interface LayoutFormButtonSubmitProps {
  label: string;
  onPress: () => void;
}

const LayoutFormButtonSubmitBlue: React.FC<LayoutFormButtonSubmitProps> = ({
  label,
  onPress,
}) => {
  return (
    <SubmitButtonBlue onPress={onPress}>
      <TextButtonWhite>{label}</TextButtonWhite>
    </SubmitButtonBlue>
  );
};

export default LayoutFormButtonSubmitBlue;