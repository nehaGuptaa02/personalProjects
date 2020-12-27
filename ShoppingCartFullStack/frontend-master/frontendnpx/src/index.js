import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import * as serviceWorker from './serviceWorker';
import {Provider} from 'react-redux';
import {createStore,applyMiddleware,compose,combineReducers} from 'redux';
import adminReducer from './store/reducers/admin';
import categoryReducer from './store/reducers/category';
import authReducer from './store/reducers/auth';
import yourAccount from './store/reducers/yourAccount';
import productReducer from './store/reducers/product';
import thunk from 'redux-thunk';
import storage from 'redux-persist/lib/storage';
import { persistStore, persistReducer } from 'redux-persist';
import { createBrowserHistory } from 'history';

export const browserHistory = createBrowserHistory();

const rootReducer=combineReducers({
  admin:adminReducer,
  auth:authReducer,
  customer:yourAccount,
  category:categoryReducer,
  product:productReducer
});
// const persistConfig = {
//   key: 'productDetails',
//   storage: storage,
//   whitelist: ['productReducer'] // which reducer want to store
// };
// const pReducer = persistReducer(persistConfig, rootReducer);



const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store=createStore(rootReducer,composeEnhancers(applyMiddleware(thunk)) );

const app = (
  <Provider store={store}>
  <BrowserRouter>
    <App />
  </BrowserRouter>
   </Provider>
);
ReactDOM.render(
 
  <React.StrictMode>
    {app}
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
