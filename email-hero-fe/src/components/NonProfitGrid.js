import { Box, Button } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import AddNonProfitOrganisationForm from "./AddNonProfitOrganisationForm";
import { getAllNonProfits, sendEmail } from "../api";
import { setNonProfitOrgs } from "../redux/nonProfit.slice";
import { AlertStatus, showAlert } from "../redux/alert.slice";

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
        const resp = await sendEmail(selectedEmails);
        if (resp.success) {
            if (resp.failureEmails.length > 0) {
                const failureEmailsStr = resp.failureEmails.reduce((acc, val) => `${acc}, ${val}`, "");
                dispatch(showAlert({
                    show: true,
                    status: AlertStatus.WARNING,
                    message: `Failed for ${failureEmailsStr}`
                }));
            } else {
                dispatch(showAlert({
                    show: true,
                    status: AlertStatus.SUCCESS,
                    message: `Successfully sent!`
                }));
            }
        } else {
            dispatch(showAlert({
                show: true,
                status: AlertStatus.ERROR,
                message: `Failed to send email!`
            }));
        }
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