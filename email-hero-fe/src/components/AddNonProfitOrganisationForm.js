import { Alert, Button, CircularProgress, Dialog, DialogTitle, Modal, TextField } from "@mui/material";
import { useState } from "react";
import { addNonProfit } from "../api";
import { addNonProfitOrg } from "../redux/nonProfit.slice";
import { useDispatch } from "react-redux";

const AddNonProfitOrganisationForm = ({ close }) => {
    const [data, setData] = useState({
        name: "",
        email: "",
        address: "",
    });
    const [showAlert, setShowAlert] = useState(false);
    const [loading, setLoading] = useState(false);
    const dispatch = useDispatch();

    const handleChange = (event) => {
        setData(prevState => {
            return {
                ...prevState,
                [event.target.name]: event.target.value
            }
        });
    }

    const handleSubmit = async () => {
        setLoading(true);
        const resp = await addNonProfit(data);
        if (!resp) {
            setShowAlert(true);
        } else {
            dispatch(addNonProfitOrg(resp));
            setData({
                name: "",
                email: "",
                address: "",
            });
        }
        setLoading(false);
    }
    return(
        <Dialog open onClose={close}>
            <DialogTitle> Add new nonprofit organisation </DialogTitle>
            {showAlert && <Alert severity="error"> Failed to add non profit organisation </Alert>}
            <form
                style={{ width: "500px", display: "flex", flexDirection: "column", gap: "20px", padding: "40px" }}>
                <TextField 
                    onChange={handleChange}
                    variant="standard" label="Enter email" name="email" required sx={{ width: "100%" }}/>
                <TextField 
                    onChange={handleChange}
                    variant="standard" label="Enter name" name="name" required sx={{ width: "100%" }}/>
                <TextField 
                    onChange={handleChange}
                    variant="standard" label="Enter address" name="address" required sx={{ width: "100%" }}/>

                <Button variant="contained" type="button" onClick={handleSubmit} disabled={loading}>
                    {loading && <CircularProgress />}
                    Add
                </Button>
            </form>
        </Dialog>
    )
}

export default AddNonProfitOrganisationForm;