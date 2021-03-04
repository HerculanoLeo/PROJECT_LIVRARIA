import axios from 'axios';
import { baseHeaders, baseUrl } from '../constants';

export const api = axios.create({ baseURL: baseUrl, headers: baseHeaders });