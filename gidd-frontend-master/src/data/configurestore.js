
import { applyMiddleware, createStore, compose } from 'redux';
import thunkMiddleware from 'redux-thunk';
import { composeWithDevTools } from 'redux-devtools-extension';
import { createLogger } from 'redux-logger';

import createRootReducer from './reducers/index';




function composeMiddleware() {
  if (process.env.ENVIRONMENT === 'development') {
    const loggerMiddleware = createLogger({
      collapsed: true,
    });
    return composeWithDevTools(
      applyMiddleware(thunkMiddleware, loggerMiddleware)
    );
  }
  return compose(applyMiddleware(thunkMiddleware));
}


export default function configureStore(initialState = {}) {
  const store = createStore(
    createRootReducer(),
    initialState,
    composeMiddleware()
  );
  console.log(store);
  return store;
}