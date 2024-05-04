import { Box, Button } from "@mui/material";
import { styled } from '@mui/material/styles';
import { DataGrid } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { getAllGrants, uploadCsvFile } from "../api";
import { AlertStatus, showAlert } from "../redux/alert.slice";

const columns = [
    { field: 'nonProfitName', headerName: 'Non Profit', width: 200 },
    { field: 'grantSubmissionName', headerName: 'Grant Submission', width: 200 },
    { field: 'stage', headerName: 'Current Stage', width: 100 },
    { field:  'requestedAmount', headerName: 'Requested Amount', width: 150},
    { field: 'awardedAmount', headerName: 'Awarded Amount', width: 150 },
    { field: 'grantType', headerName: 'Grant Type', width: 200 },
    { field: 'tags', headerName: 'Tags', width: 200 },
    { field: 'startDate', headerName: 'Start Date', width: 150 },
    { field: 'endDate', headerName: 'End Date', width: 150 }
];


const VisuallyHiddenInput = styled('input')({
    clip: 'rect(0 0 0 0)',
    clipPath: 'inset(50%)',
    height: 1,
    overflow: 'hidden',
    position: 'absolute',
    bottom: 0,
    left: 0,
    whiteSpace: 'nowrap',
    width: 1,
  });



const GrantPage = () => {
    const [loading, setLoading] = useState(false);
    const [rows, setRows] = useState([]);
    const [hasNextPage, setHasNextPage] = useState(false);
    const [page, setPage] = useState({
        page: 0,
        pageSize: 5
    });
    const dispatch = useDispatch();

    const populateData = async () => {
        setLoading(true);
        const { data, success, hasNextPage } = await getAllGrants(page.page, page.pageSize);
        if (success) {
            const processedData = data.map((record) => {
                const tags = record.tags.join(",");
                return {
                    ...record,
                    tags: tags,
                    id: record.grantSubmissionName
                }
            });
            setRows(processedData);
            setHasNextPage(hasNextPage);
        } else {
            dispatch(showAlert({
                show: true,
                message: 'Failed to fetch grant records!',
                status: AlertStatus.ERROR
              }));
        }
        setLoading(false);
    }

    useEffect(() => {
        populateData();
    }, [page]);

    const handleChange = async (event) => {
        const file = event.target.files[0];
        setLoading(true);
        const resp = await uploadCsvFile(file);
        if (!resp) {
            dispatch(showAlert({
                show: true,
                message: 'Failed to upload CSV file!',
                status: AlertStatus.ERROR
              }));
        } else {
            dispatch(showAlert({
                show: true,
                message: 'Uploaded CSV file!',
                status: AlertStatus.SUCCESS
              }));
        }

        await populateData();

        setLoading(false);
    }


    return (
        <Box sx={{ display: "flex", flexDirection: "column", gap: "20px" }}>
            <DataGrid
                pageSizeOptions={[5, 10, 25]}
                loading={loading}
                columns={columns} 
                rowCount={-1}
                rows={rows} 
                checkboxSelection 
                disableRowSelectionOnClick
                paginationMode="server"
                initialState={{ pagination: 5 }}
                paginationMeta={{
                    hasNextPage: hasNextPage
                }}
                paginationModel={page}
                onPaginationModelChange={setPage}
                sx={{ width: "1500px", minHeight: "300px" }}/>

            <Box sx={{ alignSelf: "flex-end", display: "flex", gap: "20px" }}>
                <Button
                    component="label"
                    role={undefined}
                    variant="contained"
                    disabled={loading}
                    loading={loading.toString()}
                    tabIndex={-1}>
                        Import from CSV
                        <VisuallyHiddenInput type="file" onChange={handleChange}/>
                </Button>
            </Box>
        </Box>
    )
}

export default GrantPage;