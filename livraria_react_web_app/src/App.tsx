import React from "react";

import { Provider } from "react-redux";
import store from "./redux/store";

import Routes from "./routes";
import Toast from './components/Toast'

function App() {
  return (
    <Provider store={store}>
      <Toast />
      <Routes />
    </Provider>
  );
}

export default App;
