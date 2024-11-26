import axios from "axios";

const client = axios.create({
    baseURL: "",
    headers: {
        "Content-Type": "application/json",
    }
})

// client.interceptors.request(() => {
//     // 인증로직
// })

export default client;
