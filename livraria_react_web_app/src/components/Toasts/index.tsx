import React from "react";

import { connect } from "react-redux";

import { ToastState } from "../../interfaces/Toast/toast";
import { ApplicationState } from "../../redux/reducers";
import { ToastContainer } from "./styled";
import Toast from "./Toast";

const Toasts: React.FC<ToastState> = ({ toasts }) => {
  if (toasts?.length) {
    return (
      <ToastContainer>
        {toasts.map((value, index) => (
          <Toast key={index} message={value.message} time={value.time} type={value.type} uuid={value.uuid} />
        ))}
      </ToastContainer>
    );
  } else {
    return <></>;
  }
};

const mapToStateProps = ({ toast }: ApplicationState) => ({
  ...toast,
});

export default connect(mapToStateProps)(Toasts);
