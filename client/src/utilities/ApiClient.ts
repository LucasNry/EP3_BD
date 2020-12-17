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

            return response.data;
        } catch (error) {
            console.log(error);
        }
    }

    async getFromQuery(endpoint: string, params: object) {
        try {
            const response = await this.client.get(endpoint, {
                params: params
            });

            return response.data;
        } catch (error) {
            console.log(error);
        }
    }

    async post(payload: any, object : any) {
        try {
            let endpoint: string = this.getEndpoint(object);
            const response = await this.client.post(endpoint, payload);

            return response;
        } catch (error) {
            console.log(error);
        }
    }

    getEndpoint(object : any) {
        return object.constructor.name.toLowerCase();
    }
}