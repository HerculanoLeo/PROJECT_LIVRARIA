import { ENV, BASE_URL } from "@env";
import { getLocale } from "../utils/tools";

const baseUrl = BASE_URL;

const baseHeaders = {
    'Content-Type': 'application/json',
    'Accept-Language': getLocale()
}

export { baseUrl, baseHeaders }