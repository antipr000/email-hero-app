import { Box, Button } from "@mui/material";
import ReactQuill from "react-quill";
import 'react-quill/dist/quill.snow.css';

const EmailEditor = ({ value, onChange, update, loading }) => {

    return (
        <Box sx={{ height: "auto", position: "relative" }}>
            <ReactQuill 
                theme="snow" 
                onChange={onChange} 
                value={value}
                style={{
                    height: "500px",
                    width: "1000px"
                }}/>

            <Button sx={{
                position: "absolute",
                zIndex: 10,
                top: 5,
                right: 10
            }} variant="contained" onClick={update} disabled={loading} size="small">
                Save
            </Button>
        </Box>
    )
}

export default EmailEditor;