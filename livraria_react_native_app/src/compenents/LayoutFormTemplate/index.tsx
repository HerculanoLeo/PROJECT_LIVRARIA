import React, {useEffect} from 'react';
import {Animated, Dimensions, PanResponder, StyleSheet} from 'react-native';

import {
  faCheckCircle,
  faExclamationCircle,
  faExclamationTriangle,
  faTimesCircle,
} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from '@fortawesome/react-native-fontawesome';
import {connect, useDispatch} from 'react-redux';

import {
  ERROR_MESSAGE,
  SUCCESS_MESSAGE,
  WARN_MESSAGE,
} from '../../interfaces/Message';
import {cleanMessage} from '../../redux/actions/Message';
import {ApplicationState} from '../../redux/reducers';

import {
  SubmitButtonBlue,
  SubmitButtonGray,
  TextButtonWhite,
  LayoutContainer,
  LayoutContentContainer,
  FormDescription,
  FormDescriptionContainer,
  FormTitle,
  FormTitleContainer,
  InputContainer,
  InputErrorText,
  InputLabel,
  InputDescription,
  InputRow,
  LayoutScrollViewContainer,
  FormSectionInfoContainer,
  FormSectionInfo,
  MessageContainer,
  TextMessage,
  MessageCard,
} from './styled';

interface LayoutTemplateProps {}

interface LayoutFormTitleProps {
  title: string;
}

interface LayoutFormDescriptionProps {
  description: string;
}

interface LayoutFormSectionProps {
  description: string;
}

interface LayoutFormInputProps {
  label: string;
  description?: string;
  isError?: object;
  textErrors?: string;
}

interface LayoutFormButtonSubmitProps {
  label: string;
  onPress: () => void;
}

interface LayoutFormMassage {
  type: string;
  message: string;
}

export const LayoutFormTemplate: React.FC<LayoutTemplateProps> = ({
  children,
}) => {
  return (
    <LayoutContainer>
      <LayoutScrollViewContainer alwaysBounceVertical={false} keyboardShouldPersistTaps={'handled'}>
        <LayoutContentContainer>{children}</LayoutContentContainer>
      </LayoutScrollViewContainer>
    </LayoutContainer>
  );
};

export const LayoutFormTitle: React.FC<LayoutFormTitleProps> = ({title}) => {
  return (
    <FormTitleContainer>
      <FormTitle>{title}</FormTitle>
    </FormTitleContainer>
  );
};

export const LayoutFormDescription: React.FC<LayoutFormDescriptionProps> = ({
  description,
}) => {
  return (
    <FormDescriptionContainer>
      <FormDescription>{description}</FormDescription>
    </FormDescriptionContainer>
  );
};

export const LayoutFormSection: React.FC<LayoutFormSectionProps> = ({
  description,
}) => {
  return (
    <FormSectionInfoContainer>
      <FormSectionInfo>{description}</FormSectionInfo>
    </FormSectionInfoContainer>
  );
};

export const LayoutFormInput: React.FC<LayoutFormInputProps> = ({
  label,
  description,
  children,
  isError,
  textErrors,
}) => {
  return (
    <InputContainer>
      <InputLabel>{label}{description && (<InputDescription> ({description})</InputDescription>)}</InputLabel>
      
      <InputRow style={styles.shadowInput} isError={isError ? true : false}>
        {children}
      </InputRow>
      {isError ? <InputErrorText>* {textErrors}</InputErrorText> : <></>}
    </InputContainer>
  );
};

export const LayoutFormButtonSubmitBlue: React.FC<LayoutFormButtonSubmitProps> = ({
  label,
  onPress,
}) => {
  return (
    <SubmitButtonBlue onPress={onPress}>
      <TextButtonWhite>{label}</TextButtonWhite>
    </SubmitButtonBlue>
  );
};

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

const _LayoutFormMassage: React.FC<LayoutFormMassage> = ({type, message}) => {
  const witdhScreen = Dimensions.get('screen').width;

  const dispatch = useDispatch();

  const MessageCardAnimeted = Animated.createAnimatedComponent(MessageCard);

  const pan = new Animated.ValueXY({x: -1000, y: 0});

  const position = new Animated.Value(0.5);

  const opacity = position.interpolate({
    inputRange: [0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1],
    outputRange: [0, 0.1, 0.8, 1, 1, 1, 0.8, 0.3, 0.2, 0.1, 0],
  });

  const panResponder = PanResponder.create({
    onMoveShouldSetPanResponder: () => true,
    onPanResponderMove: (ev, gh) => {
      pan.setValue({x: gh.dx, y: 0});
      position.setValue(gh.moveX / witdhScreen);
    },
    onPanResponderRelease: (ev, gh) => {
      const percentOfScreen = gh.moveX / witdhScreen;

      if (percentOfScreen > 0.7 || percentOfScreen < 0.1) {

        dispatch(cleanMessage());
        
      } else {
        position.setValue(0.5);
        Animated.spring(pan, {
          toValue: {x: 0, y: 0},
          useNativeDriver: true,
        }).start();
      }
    },
  });

  useEffect(() => {
    Animated.spring(pan, {
      toValue: {x: 0, y: 0},
      useNativeDriver: true,
    }).start();
  }, [type, message]);

  const colorType = () => {
    switch (type) {
      case SUCCESS_MESSAGE: {
        return {
          color: '#84ff84AD',
          icon: faCheckCircle,
        };
      }

      case WARN_MESSAGE: {
        return {
          color: '#ecff7dAD',
          icon: faExclamationTriangle,
        };
      }

      case ERROR_MESSAGE: {
        return {
          color: '#ff7676BF',
          icon: faTimesCircle,
        };
      }

      default: {
        return {
          color: '#86fff9AB',
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
            transform: [{translateX: pan.x}, {translateY: pan.y}],
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

const mapMessageProps = ({message}: ApplicationState): LayoutFormMassage => ({
  type: message.type,
  message: message.message,
});

export const LayoutFormMassage = connect(mapMessageProps)(_LayoutFormMassage);

const styles = StyleSheet.create({
  shadowInput: {
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 1,
    },
    shadowOpacity: 0.2,
    shadowRadius: 1.41,
    elevation: 2,
  },
});
