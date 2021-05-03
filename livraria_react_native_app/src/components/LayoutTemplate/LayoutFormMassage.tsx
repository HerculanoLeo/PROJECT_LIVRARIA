import React, { useEffect } from "react";
import { Dimensions, Animated, PanResponder } from "react-native";
import { useDispatch, connect } from "react-redux";
import { faCheckCircle, faExclamationTriangle, faTimesCircle, faExclamationCircle } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";

import { SUCCESS_MESSAGE, WARN_MESSAGE, ERROR_MESSAGE } from "../../interfaces/Message";
import { cleanMessage } from "../../redux/actions/Message";
import { ApplicationState } from "../../redux/reducers";
import { MessageCard, MessageContainer, TextMessage } from "./styled";


interface LayoutFormMassage {
  type: string;
  message: string;
}

const LayoutFormMassage: React.FC<LayoutFormMassage> = ({ type, message }) => {
  const witdhScreen = Dimensions.get('screen').width;

  const dispatch = useDispatch();

  const MessageCardAnimeted = Animated.createAnimatedComponent(MessageCard);

  const pan = new Animated.ValueXY({ x: -1000, y: 0 });

  const position = new Animated.Value(0.5);

  const opacity = position.interpolate({
    inputRange: [0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1],
    outputRange: [0, 0.1, 0.8, 1, 1, 1, 0.8, 0.3, 0.2, 0.1, 0],
  });

  const panResponder = PanResponder.create({
    onMoveShouldSetPanResponder: () => true,
    onPanResponderMove: (ev, gh) => {
      pan.setValue({ x: gh.dx, y: 0 });
      position.setValue(gh.moveX / witdhScreen);
    },
    onPanResponderRelease: (ev, gh) => {
      const percentOfScreen = gh.moveX / witdhScreen;

      if (percentOfScreen < 0.2 || percentOfScreen > 0.8) {

        dispatch(cleanMessage());

      } else {
        position.setValue(0.5);
        Animated.spring(pan, {
          toValue: { x: 0, y: 0 },
          useNativeDriver: true,
        }).start();
      }
    },
  });

  useEffect(() => {
    Animated.spring(pan, {
      toValue: { x: 0, y: 0 },
      useNativeDriver: true,
    }).start();
  }, [type, message]);

  const colorType = () => {
    switch (type) {
      case SUCCESS_MESSAGE: {
        return {
          color: '#84ff84BD',
          icon: faCheckCircle,
        };
      }

      case WARN_MESSAGE: {
        return {
          color: '#ecff7dBD',
          icon: faExclamationTriangle,
        };
      }

      case ERROR_MESSAGE: {
        return {
          color: '#ff7676BD',
          icon: faTimesCircle,
        };
      }

      default: {
        return {
          color: '#86fff9BD',
          icon: faExclamationCircle,
        };
      }
    }
  };

  if (message) {
    return (
      <MessageContainer>
        <MessageCardAnimeted
          color={colorType().color}
          style={{
            transform: [{ translateX: pan.x }, { translateY: pan.y }],
            opacity: opacity,
          }}
          {...panResponder.panHandlers}>
          <FontAwesomeIcon icon={colorType().icon} size={24} />
          <TextMessage>{message}</TextMessage>
        </MessageCardAnimeted>
      </MessageContainer>
    );
  } else {
    return <></>;
  }
};

const mapStateToProps = ({ message }: ApplicationState): LayoutFormMassage => ({
  type: message.type,
  message: message.message,
});

const mapDispatchsToProps = ({ }) => ({

})

export default connect(mapStateToProps)(LayoutFormMassage);