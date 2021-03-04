import { createStore, applyMiddleware } from "redux";
import { createLogger } from "redux-logger";
import { rootReducer } from "./reducers";
import thunk from "redux-thunk";

const loggerMiddleware = createLogger();

export default createStore(rootReducer, applyMiddleware(thunk, loggerMiddleware));
