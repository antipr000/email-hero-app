import { createSlice } from "@reduxjs/toolkit";

const userSlice = createSlice({
    name: 'user',
    initialState: { email: null },
    reducers: {
        loginUser(state, action) {
            state.email = action.payload
        }
    }
});

export const { loginUser } = userSlice.actions;

export default userSlice.reducer;
