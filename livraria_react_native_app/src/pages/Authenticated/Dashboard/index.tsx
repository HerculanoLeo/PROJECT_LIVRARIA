import React, { useRef, useState } from 'react';
import { } from 'react-native';
import { useDispatch } from 'react-redux';

import LayoutFormButtonSubmitBlue from '../../../components/LayoutTemplate/LayoutFormButtonSubmitBlue';


import { LayoutContainer } from '../../../components/LayoutTemplate/styled';
import { logoutRequest } from '../../../redux/actions/Login';


const Dashboard: React.FC = () => {
  const dispatchRedux = useDispatch();

  return (
    <LayoutContainer>
      <LayoutFormButtonSubmitBlue label="Logout" onPress={() => dispatchRedux(logoutRequest())} />
    </LayoutContainer>
  )
}

export default Dashboard;
