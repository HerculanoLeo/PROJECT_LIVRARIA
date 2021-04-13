import React from "react";

import { connect } from "react-redux";
import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { faCheckCircle, faExclamationTriangle, faInfoCircle, faTimesCircle } from "@fortawesome/free-solid-svg-icons";

import { MessageState, MESSAGE_ERROR, MESSAGE_INFO, MESSAGE_SUCCESS, MESSAGE_WARN } from "../../../interfaces/Message";
import { ApplicationState } from "../../../redux/reducers";

import { Container } from "./styled";

interface MessageTypesComponent {
  color: string;
  Icon: IconProp;
}

const Messages: React.FC<MessageState> = ({ type, messages }) => {
  const ToastColor = (): MessageTypesComponent => {
    switch (type) {
      case MESSAGE_SUCCESS: {
        return {
          color: "#00ff62",
          Icon: faCheckCircle,
        };
      }

      case MESSAGE_INFO: {
        return {
          color: "#2f96b4",
          Icon: faInfoCircle,
        };
      }

      case MESSAGE_WARN: {
        return {
          color: "#fffb00",
          Icon: faExclamationTriangle,
        };
      }

      case MESSAGE_ERROR: {
        return {
          color: "#ff0000",
          Icon: faTimesCircle,
        };
      }

      default: {
        return {
          color: "#2f96b4",
          Icon: faCheckCircle,
        };
      }
    }
  };

  if (messages.length >= 1) {
    return (
      <Container>
        <div>{messages}</div>
      </Container>
    );
  } else {
    return <></>;
  }
};

const mapStateToProps = ({ message }: ApplicationState): MessageState => ({
  ...message,
});

export default connect(mapStateToProps)(Messages);
