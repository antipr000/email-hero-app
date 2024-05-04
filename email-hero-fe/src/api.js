import axios from "axios";
import { store } from "./redux/store";

const instance = axios.create({
    baseURL: "http://localhost:8080"
});

instance.interceptors.request.use((config) => {
    const email = store.getState()?.user?.email;
    config.headers['Email'] = email;
    return config;
})


async function login(email) {
    try{
        await instance.post("/login", { email });
        return email;
    } catch (e) {
        return null;
    }
}

async function addNonProfit({ email, name, address }) {
    try {
        const { data } = await instance.post("/nonprofit", { email, name, address });
        return data;
    } catch (e) {
        return null;
    }
}

async function getAllNonProfits() {
    try {
        const { data } = await instance.get("/nonprofit");
        return data;
    } catch (e) {
        return [];
    }
}

async function getEmailTemplate() {
    try {
        const { data } = await instance.get("/template");
        return data.template;
    } catch (e) {
        return null;
    }
}

async function addEmailTemplate(template) {
    try {
        await instance.post("/template", { template });
        return true;
    } catch (e) {
        return false;
    }
}

async function sendEmail(emails) {
    try {
        const {data} = await instance.post("/sendemail",  { nonProfitEmails: emails });
        return {
            success: true,
            failureEmails: data.failureEmails
        }
    } catch (e) {
        return {
            success: false,
            failureEmails: []
        }
    }
}

async function getAllEmails() {
    try {
        const { data } = await instance.get('/emails');
        return data;
    } catch (e) {
        return [];
    }
}

export { login, 
        addNonProfit, 
        getAllNonProfits, 
        getEmailTemplate, 
        addEmailTemplate, 
        sendEmail,
        getAllEmails };