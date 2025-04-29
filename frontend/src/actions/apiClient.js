
import axios from 'axios'
const baseURL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:80';

const apiClient = axios.create({
  baseURL: baseURL,
  timeout: 10000,
});

export {apiClient};
