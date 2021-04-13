import { getLocale } from "../utils";

const baseUrl = process.env.REACT_APP_BASE_URL;

console.log(process.env.REACT_APP_BASE_URL);


const baseHeaders = {
    'Content-Type': 'application/json',
    'Accept-Language': getLocale()
}

export { baseUrl, baseHeaders }