import Axios, { AxiosInstance } from "axios";

export default class ApiClient {

    apiUrl : string = "http://127.0.0.1:8080/";

    client : AxiosInstance = Axios.create({
        baseURL: this.apiUrl
    });
    
    async get(object : any) {
        try {
            const response = await this.client.get(this.getEndpoint(object), {
                params: object
            });

            return response;
        } catch (error) {
            console.log(error);
        }
    }

    async post(object : any) {
        try {
            const response = await this.client.post(this.getEndpoint(object), object);

            return response;
        } catch (error) {
            console.log(error);
        }
    }

    getEndpoint(object : any) {
        return object.constructor.name.toLowerCase();
    }
}