import { Snackbar } from "@mui/base";
import { Alert } from "@mui/material";
import { useDispatch, useSelector } from "react-redux"
import { AlertStatus, showAlert } from "../redux/alert.slice";

const PopupAlert = () => {
    const { show, message, status } = useSelector(state => state.alert);
    console.log("Changed", show, status);
    const dispatch = useDispatch();
    const handleClose = () => {
        dispatch(showAlert({
            show: false,
            message: '',
            status: AlertStatus.SUCCESS
        }));
    }

    return(
        <Snackbar 
            open={show} 
            autoHideDuration={3000} 
            onClose={handleClose}
            anchorOrigin={{vertical: 'top', horizontal: 'left'}}
            sx={{marginTop: "100px", zIndex: 100000}}>
            <Alert severity={status} variant="filled" sx={{ width: "200px", marginLeft: "20px" }}>
                {message}
            </Alert>
        </Snackbar>
    )
}


export default PopupAlert;