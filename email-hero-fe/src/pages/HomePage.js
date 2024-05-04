import { Box, AppBar, Toolbar, Button, Typography } from "@mui/material";
import NonProfitGrid from "../components/NonProfitGrid";
import { useState } from "react";
import EmailTemplate from "../components/EmailTemplate";
import PopupAlert from "../components/PopupAlert";
import GrantPage from "./GrantPage";

const navItems = ['Home', 'Email Template', 'Grants'];

const TabContent = ({ tab }) => {
    switch(tab) {
        case "Home":
            return <NonProfitGrid />
        case "Email Template":
            return <EmailTemplate />
        case "Grants":
            return <GrantPage />
    }
}

const HomePage = () => {
    const [tab, selectedTab] = useState("Home");
    return(
        <Box>
            <AppBar component="nav">
                <Toolbar>
                    <Typography
                        variant="h6"
                        component="div"
                        sx={{ flexGrow: 1, display: { xs: 'none', sm: 'block' } }}
                    >
                        Email Hero
                    </Typography>
                    <Box sx={{ display: { xs: 'none', sm: 'block' } }}>
                        {navItems.map((item) => (
                        <Button key={item} sx={{ color: '#fff' }} onClick={() => selectedTab(item)}>
                            {item}
                        </Button>
                        ))}
                    </Box>
                </Toolbar>
            </AppBar>
            <Box sx={{ marginTop: "100px" }}>
                <PopupAlert />
                <Box sx={{ marginTop: "10px", height: "auto", display: "flex", justifyContent: "center"}}>
                    <TabContent tab={tab}/>
                </Box>
            </Box>
        </Box>
    )
}


export default HomePage;