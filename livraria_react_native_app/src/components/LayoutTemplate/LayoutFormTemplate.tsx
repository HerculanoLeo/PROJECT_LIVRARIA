import React from "react";
import { Platform } from "react-native";

import { LayoutContainer, LayoutContentContainer, LayoutScrollViewContainer } from "./styled";

interface LayoutTemplateProps { }

const LayoutFormTemplate: React.FC<LayoutTemplateProps> = ({ children }) => {
  return (
    <LayoutContainer>
      <LayoutContentContainer behavior={Platform.OS === "ios" ? "padding" : "height"}>
        <LayoutScrollViewContainer alwaysBounceVertical={false} keyboardShouldPersistTaps={'handled'}>
          {children}
        </LayoutScrollViewContainer>
      </LayoutContentContainer>
    </LayoutContainer>
  );
};

export default LayoutFormTemplate;