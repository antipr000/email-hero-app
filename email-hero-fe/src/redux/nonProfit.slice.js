import { createSlice } from "@reduxjs/toolkit";

const nonProfitSlice = createSlice({
    name: 'nonProfit',
    initialState: {
        orgs: []
    },
    reducers: {
        addNonProfitOrg(state, action) {
            state.orgs.push({
                email: action.payload.email,
                name: action.payload.name,
                address: action.payload.address
            })
        },
        setNonProfitOrgs(state, action) {
            state.orgs = action.payload;
        }
    }
});

export const { addNonProfitOrg, setNonProfitOrgs } = nonProfitSlice.actions;

export default nonProfitSlice.reducer;
