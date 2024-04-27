import { Box, AppBar, Toolbar, Button, Typography } from "@mui/material";
import NonProfitGrid from "../components/NonProfitGrid";
import { useState } from "react";
import EmailTemplate from "../components/EmailTemplate";

const navItems = ['Home', 'Email Template'];

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
            <Box sx={{ marginTop: "100px", height: "auto", display: "flex", justifyContent: "center" }}>
                {tab === "Home" ? <NonProfitGrid /> : <EmailTemplate />}
            </Box>
        </Box>
    )
}


export default HomePage;