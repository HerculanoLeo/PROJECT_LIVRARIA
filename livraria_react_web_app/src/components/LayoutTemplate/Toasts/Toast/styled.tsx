import styled from "styled-components";

interface ToastContainerProps {
  color: string;
}

interface ToastProgressBarProps {
  time: number;
}

export const ToastProgressBar = styled.div<ToastProgressBarProps>`
  height: 10px;
  background-color: #fff;
  animation: progressBarAnimationStrike ${(props) => `${props.time}s`};

  @keyframes progressBarAnimationStrike {
    from {
      width: 100%;
    }
    to {
      width: 0;
    }
  }
`;

export const ToastContainer = styled.div<ToastContainerProps>`
  position: relative;

  display: flex;
  flex-direction: row;
  align-items: flex-start;
  justify-content: stretch;

  height: 110px;
  width: 380px;
  margin: 5px 10px;
  border-radius: 4px;
  border: none;

  -webkit-box-shadow: 0px 0px 5px 2px rgba(0, 0, 0, 0.1);
  box-shadow: 0px 0px 5px 2px rgba(0, 0, 0, 0.1);

  background-color: ${(props) => `${props.color}`};

  animation: progressToastAnimationStrike 0.5s;

  @keyframes progressToastAnimationStrike {
    from {
      right: -400px;
    }
    to {
      right: 10px;
    }
  }

  &:hover ${ToastProgressBar} {
    animation-play-state: paused;
  }
`;

export const ToastIconContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  height: 100%;
  width: 25%;

  border-right: 1px solid #c4c4c4;
`;

export const ToastContentContainer = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: flex-start;

  height: 100%;
  width: 70%;
`;

export const ToastContentHeader = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: stretch;
  height: 20px;

  border-bottom: 1px solid #c4c4c4;
`;

export const ToastHeaderTitle = styled.div`
  flex: 1;
  color: #fff;
  text-align: center;
  font-weight: bold;
`;

export const ToastHeaderButtonClose = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
  margin-right: 5px;
  z-index: 1;

  cursor: pointer;
`;

export const ToastContent = styled.div`
  flex: 1;
  display: flex;
  align-items: stretch;
  justify-content: center;
  width: 100%;
  text-align: left;
  color: #fff;
  overflow-y: auto;
`;

export const ToastProgressBarBackgrourd = styled.div`
  height: 10px;
  width: 100%;

  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
`;
