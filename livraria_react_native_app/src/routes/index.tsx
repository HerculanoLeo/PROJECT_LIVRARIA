import React, {useContext} from 'react';

import AuthContext from '../contexts/auth';
import AuthRoute from './auth.routes';
import AppRoute from './app.routes';

const Routes = () => {
  const {signed} = useContext(AuthContext);

  return signed ? <AppRoute /> : <AuthRoute />;
};

export default Routes;
