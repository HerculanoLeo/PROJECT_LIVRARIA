import React from 'react';
import { View } from 'react-native';

import { faMinusSquare, faPlusSquare } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';

import { PlusMinusButton, PlusMinusContainer, PlusMinusLabel, PlusMinusLabelContainer } from './styled';

interface PlusMinusCounterProps {
  value: number;
  step?: number;
  min?: number;
  max?: number;
  onChange(counter: number): void;
  remove?(num: number): void;
  add?(num: number): void;
}

const PlusMinusCounter: React.FC<PlusMinusCounterProps> = ({ value, step = 1, min, max, onChange = (value) => null, remove, add }) => {

  const plusCounter = () => {
    onChange(value + step);

    return add && add(step);
  }

  const minusCounter = () => {
    onChange(value - step);

    return remove && remove(-step);
  }

  const isPlusAvailable = () => {
    if(!max) return true;
    return max > value;
  }

  const isMinusAvailable = () => {
    if(!min) return true;
    return min < value;
  }

  return (
    <PlusMinusContainer>
      <PlusMinusButton 
      onPress={minusCounter} 
      style={{
        opacity: isMinusAvailable() ? 1 : 0.4,
      }}
      disabled={!isMinusAvailable()}
      >
        <FontAwesomeIcon icon={faMinusSquare} color="#000" />
      </PlusMinusButton>

      <PlusMinusLabelContainer>
        <PlusMinusLabel>{value}</PlusMinusLabel>
      </PlusMinusLabelContainer>

      <PlusMinusButton onPress={plusCounter} style={{
        opacity: isPlusAvailable() ? 1 : 0.4,
      }}>
        <FontAwesomeIcon icon={faPlusSquare} color="#000" />
      </PlusMinusButton>
    </PlusMinusContainer>
  )
}

export default PlusMinusCounter;