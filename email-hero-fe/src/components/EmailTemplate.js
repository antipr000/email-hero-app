import { Box, Button } from '@mui/material';
import React, { useEffect, useState } from 'react';
import EmailEditor from './EmailEditor';
import { addEmailTemplate, getEmailTemplate } from '../api';

const EmailTemplate = () => {
  const [value, setValue] = useState('');
  const [loading, setLoading] = useState(false);

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
    await addEmailTemplate(value);
  }

  return (
    <Box sx={{ display: "flex", flexDirection: "column", gap: "20px" }}>
        <EmailEditor value={value} onChange={setValue} loading={loading} update={updateEmailTemplate}/>
    </Box>
  )
}

export default EmailTemplate;