import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { faCheckCircle, faExclamationTriangle, faInfoCircle, faTimesCircle } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useState } from "react";

import { connect, useDispatch } from "react-redux";

import { ToastState } from "../../interfaces/Toast/toast";
import { TOAST_ERROR, TOAST_HIDE, TOAST_INFO, TOAST_SUCCESS, TOAST_WARN } from "../../redux/actions/Toast/actionToastTypes";
import { ApplicationState } from "../../redux/reducers";
import { ToastDispatch } from "../../redux/reducers/toast";
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

interface ToastTypesComponent {
  title: string;
  color: string;
  Icon: IconProp;
}

interface TimerProps {
  cancel: () => void;
  pause: () => void;
  resume: () => void;
}

const Toast: React.FC<ToastState> = ({ show, type, message, time = 10 }) => {
  const dispacth = useDispatch<ToastDispatch>();

  const [timer, setTimer] = useState<TimerProps>();

  useEffect(() => {
    if (show) {
      const timer = Timer(() => dispacth({ type: TOAST_HIDE }), 5 * 1000);

      console.log(timer);

      setTimer(timer);
    }
  }, [show]);

  function Timer(fn: any, countdown: number): TimerProps {
    var ident: any;
    var complete = false;
    var total_time_run: number;
    var start_time = new Date().getTime();

    function _time_diff(date1: number, date2?: number): number {
      return date2 ? date2 - date1 : new Date().getTime() - date1;
    }

    function cancel() {
      clearTimeout(ident);
    }

    function pause() {
      clearTimeout(ident);
      total_time_run = _time_diff(start_time);
      complete = total_time_run >= countdown;
    }

    function resume() {
      console.log(complete, countdown - total_time_run);

      let time = countdown - total_time_run < 0 ? 0 : countdown - total_time_run;

      ident = complete ? -1 : setTimeout(fn, time);
    }

    ident = setTimeout(fn, countdown);

    return { cancel: cancel, pause: pause, resume: resume };
  }

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

  if (show) {
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
                dispacth({ type: TOAST_HIDE });
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
  } else {
    return <></>;
  }
};

const mapStateToProps = ({ toast }: ApplicationState): ToastState => ({
  ...toast,
});

export default connect(mapStateToProps)(Toast);
