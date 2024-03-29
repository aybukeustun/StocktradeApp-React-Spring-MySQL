import axios from "axios";
import authHeader from "./auth-header";
const API_URL = "http://localhost:8080/stocks";

class StockService {


    getAllStocks = () => {
        return axios.get(API_URL + "", {headers: authHeader()}).then((response) => 
        {
            return response.data;
        });
    };
}
export default new StockService();
