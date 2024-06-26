import { Box, Button } from '@mui/material';
import React, { useEffect, useState } from 'react';
import EmailEditor from './EmailEditor';
import { addEmailTemplate, getEmailTemplate } from '../api';
import { useDispatch } from 'react-redux';
import { AlertStatus, showAlert } from '../redux/alert.slice';

const EmailTemplate = () => {
  const [value, setValue] = useState('');
  const [loading, setLoading] = useState(false);

  const dispatch = useDispatch();

  useEffect(() => {
    async function fetchEmailTemplate() {
        setLoading(true);
        const template = await getEmailTemplate();
        if (template) {
            setValue(template);
        }
        setLoading(false);
    }

    fetchEmailTemplate();
  }, []);

  const updateEmailTemplate = async () => {
    setLoading(true);
    const result = await addEmailTemplate(value);
    if (!result) {
      dispatch(showAlert({
        show: true,
        message: 'Failed to save email template!',
        status: AlertStatus.ERROR
      }));
    } else {
      dispatch(showAlert({
        show: true,
        message: 'Saved!',
        status: AlertStatus.SUCCESS
      }));
    }

    setLoading(false);
  }

  return (
    <Box sx={{ display: "flex", flexDirection: "column", gap: "20px" }}>
        <EmailEditor value={value} onChange={setValue} loading={loading} update={updateEmailTemplate}/>
    </Box>
  )
}

export default EmailTemplate;