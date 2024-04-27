import { Alert, Box, Button, Input, Paper, TextField, Typography } from "@mui/material";
import { useState } from "react";
import { login } from "../api";
import { useDispatch } from "react-redux";
import { loginUser } from "../redux/user.slice";

const LoginPage = () => {
    const [email, setEmail] = useState("");
    const [showAlert, setShowAlert] = useState(false);
    const dispatch = useDispatch();

    const handleClick = async () => {
        const resp = await login(email);
        if (!resp) {
            setShowAlert(true);
        } else {
            dispatch(loginUser(resp));
        }
    }

    return (
        <Box sx={{
            width: "100vw",
            height: "100vh",
            display: "flex",
            justifyContent: "center",
            alignItems: "center"
        }}>
            <Paper 
                elevation={2} 
                sx={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "center",
                    alignItems: "center",
                    padding: "40px",
                    paddingLeft: "80px",
                    paddingRight: "80px",
                    gap: "20px",
                    width: "300px"
                }}>
                    <Typography sx={{ alignSelf: "flex-start" }} variant="h5"> Login </Typography>
                    {showAlert && <Alert severity="error" sx={{ width: "100%" }}> Failed to login </Alert>}
                    <TextField 
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                        variant="standard" 
                        label="Enter email" 
                        required 
                        sx={{ width: "100%" }}/>
                    <Button 
                        onClick={handleClick}
                        disabled={email === "" || email === null}
                        variant="contained" 
                        sx={{ alignSelf: "flex-end" }}> Submit </Button>
            </Paper>
        </Box>
    )
};

export default LoginPage;
