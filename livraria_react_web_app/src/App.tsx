import React from "react";

import { Provider } from "react-redux";
import Toasts from "./components/LayoutTemplate/Toasts";
import store from "./redux/store";

import Routes from "./routes";

const App: React.FC = () => {
  return (
    <Provider store={store}>
      <Toasts />
      <Routes />
    </Provider>
  );
};

export default App;
