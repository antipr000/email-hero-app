import { Box } from "@mui/material";
import { useSelector } from "react-redux";
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";

const RootContainer = () => {
    const userEmail = useSelector(state => state.user.email);
    console.log("User email is: ", userEmail);
    return(
        <Box sx={{
            width: "100vw",
            height: "100vh"
        }}>
            {userEmail ? <HomePage /> : <LoginPage /> }
        </Box>
    );
};

export default RootContainer;