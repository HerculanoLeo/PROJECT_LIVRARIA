import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { faCheckCircle, faExclamationTriangle, faInfoCircle, faTimesCircle } from "@fortawesome/free-solid-svg-icons";

import { TOAST_REMOVE } from "../../../redux/actions/Toast/actionToastTypes";
import { ToastProps, TOAST_ERROR, TOAST_INFO, TOAST_SUCCESS, TOAST_WARN } from "../../../interfaces/Toast/toast";
import { ToastDispatch } from "../../../redux/reducers/toast";
import {
  ToastHeaderButtonClose,
  ToastContainer,
  ToastContentContainer,
  ToastContentHeader,
  ToastIconContainer,
  ToastHeaderTitle,
  ToastContent,
  ToastProgressBarBackgrourd,
  ToastProgressBar,
} from "./styled";
import { Timer, TimerProps } from "../../../utils";

interface ToastTypesComponent {
  title: string;
  color: string;
  Icon: IconProp;
}

const Toast: React.FC<ToastProps> = ({ type, message, time = 10, uuid }) => {
  const dispacth = useDispatch<ToastDispatch>();

  const [timer, setTimer] = useState<TimerProps>();

  useEffect(() => {
    const timer = Timer(function () {
      dispacth({
        type: TOAST_REMOVE,
        payload: {
          uuid,
          type,
          message,
          time,
        },
      });
    }, time * 1000);


    setTimer(timer);
  }, []);

  const ToastColor = (): ToastTypesComponent => {
    switch (type) {
      case TOAST_SUCCESS: {
        return {
          title: "Sucesso",
          color: "#00ff62",
          Icon: faCheckCircle,
        };
      }

      case TOAST_INFO: {
        return {
          title: "Informação",
          color: "#2f96b4",
          Icon: faInfoCircle,
        };
      }

      case TOAST_WARN: {
        return {
          title: "Aviso",
          color: "#fffb00",
          Icon: faExclamationTriangle,
        };
      }

      case TOAST_ERROR: {
        return {
          title: "Erro",
          color: "#ff0000",
          Icon: faTimesCircle,
        };
      }

      default: {
        return {
          title: "Informação",
          color: "#2f96b4",
          Icon: faCheckCircle,
        };
      }
    }
  };

  return (
    <ToastContainer color={ToastColor().color} onMouseEnter={() => timer?.pause()} onMouseLeave={() => timer?.resume()}>
      <ToastIconContainer>
        <FontAwesomeIcon icon={ToastColor().Icon} size="3x" color="#FFF" />
      </ToastIconContainer>

      <ToastContentContainer>
        <ToastContentHeader>
          <ToastHeaderTitle>{ToastColor().title}</ToastHeaderTitle>

          <ToastHeaderButtonClose
            onClick={() => {
              timer?.cancel();
              dispacth({
                type: TOAST_REMOVE,
                payload: {
                  uuid,
                  type,
                  message,
                  time,
                },
              });
            }}
          >
            <FontAwesomeIcon icon={faTimesCircle} size="1x" color="#000000" />
          </ToastHeaderButtonClose>
        </ToastContentHeader>
        <ToastContent>
          <p>{message}</p>
        </ToastContent>

        <ToastProgressBarBackgrourd>
          <ToastProgressBar time={time} />
        </ToastProgressBarBackgrourd>
      </ToastContentContainer>
    </ToastContainer>
  );
};

export default Toast;
