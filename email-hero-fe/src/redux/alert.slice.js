import { createSlice } from "@reduxjs/toolkit";

export const AlertStatus = {
    SUCCESS: 'success',
    ERROR: 'error',
    WARNING: 'warning'
}

const alertSlice = createSlice({
    name: 'alert',
    initialState: {
        show: false,
        message: '',
        status: AlertStatus.SUCCESS
    },
    reducers: {
        showAlert(state, action) {
            const { show, message, status } = action.payload;
            state.show = show;
            state.message = message;
            state.status = status;
        }
    }
});

export const { showAlert } = alertSlice.actions;

export default alertSlice.reducer;
