import { Box, Button } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import AddNonProfitOrganisationForm from "./AddNonProfitOrganisationForm";
import { getAllNonProfits, sendEmail } from "../api";
import { setNonProfitOrgs } from "../redux/nonProfit.slice";

const columns = [
    { field: 'email', headerName: 'Email', width: 300 },
    { field: 'name', headerName: 'Name', width: 300 },
    { field: 'address', headerName: 'Address', width: 400 }
];

const NonProfitGrid = () => {
    const nonProfits = useSelector(state => state.nonProfit.orgs);
    const [loading, setLoading] = useState(false);
    const [showForm, setShowForm] = useState(null);
    const [selectedEmails, setSelectedEmails] = useState([]);
    const dispatch = useDispatch();
    const rows = nonProfits.map((data) => ({id: data.email, ...data}));

    useEffect(() => {
        async function fetchAllNonProfitOrgs() {
            setLoading(true);
            const data = await getAllNonProfits();
            dispatch(setNonProfitOrgs(data));
            setLoading(false);
        }
        fetchAllNonProfitOrgs();
    }, []);

    const sendEmailToSelectedNonProfits = async () => {
        setLoading(true);
        await sendEmail(selectedEmails);
        setLoading(false);
    }

    return(
        <Box sx={{ display: "flex", flexDirection: "column", gap: "20px" }}>
            {showForm && <AddNonProfitOrganisationForm close={() => setShowForm(false)}/>}
            <DataGrid 
            loading={loading}
            columns={columns} 
            rows={rows} 
            checkboxSelection 
            disableRowSelectionOnClick
            onRowSelectionModelChange={(newRowSelectionModel) => {
                setSelectedEmails(newRowSelectionModel)
            }}
            rowSelectionModel={selectedEmails}
            sx={{ width: "1000px", minHeight: "300px" }}/>

            <Box sx={{ alignSelf: "flex-end", display: "flex", gap: "20px" }}>
                <Button variant="contained" onClick={() => setShowForm(true)}>
                    Add nonprofit organisation
                </Button>

                <Button variant="contained" onClick={sendEmailToSelectedNonProfits} 
                disabled={loading || selectedEmails.length === 0}>
                    Send Email
                </Button>
            </Box>
        </Box>
    )
}

export default NonProfitGrid;