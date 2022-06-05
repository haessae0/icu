import axios from "axios";

function createInstance() {
  return axios.create({
    baseURL: "http://localhost:8000"
  });
}

export const instance = createInstance();
