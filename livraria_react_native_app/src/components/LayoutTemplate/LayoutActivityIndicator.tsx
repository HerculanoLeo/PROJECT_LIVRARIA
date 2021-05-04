import React from 'react';
import { ActivityIndicator } from 'react-native';
import { connect } from 'react-redux';

import { ApplicationState } from '../../redux/reducers';
import { ActivityIndicatorContainer, ActivityIndicatorIconContainer } from './styled';

interface LayoutActivityIndicatorProps {
  isLoading: boolean;
  isBlockScreen: boolean;
  isActivityIndicator: boolean;
}

const LayoutActivityIndicator: React.FC<LayoutActivityIndicatorProps> = ({ isLoading, isBlockScreen, isActivityIndicator }) => {
  return (
    <>
      {isLoading && isActivityIndicator && (
        <ActivityIndicatorContainer isBlockScreen={isBlockScreen}>
          <ActivityIndicatorIconContainer>
            <ActivityIndicator size='large' color={'#000000'} />
          </ActivityIndicatorIconContainer>
        </ActivityIndicatorContainer>
      )}
    </>
  );
}

const mapStateToProps = ({ loading }: ApplicationState): LayoutActivityIndicatorProps => ({
  isLoading: loading.isLoading,
  isBlockScreen: loading.isBlockScreen,
  isActivityIndicator: loading.isActivityIndicator
});

export default connect(mapStateToProps)(LayoutActivityIndicator);