import { configureStore } from '@reduxjs/toolkit'
import userReducer from './user.slice';
import nonProfitReducer from './nonProfit.slice';
import alertReducer from './alert.slice';

export const store = configureStore({
  reducer: {
    user: userReducer,
    nonProfit: nonProfitReducer,
    alert: alertReducer,
  },
});
