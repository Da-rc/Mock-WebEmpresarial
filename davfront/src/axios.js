
import axios from 'axios';
console.log(process.env.VUE_APP_API_URL);

const axiosInstance = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    }
})

export const getAllOficinas = () => axiosInstance.get(`/oficinas`);
export const getAllEmpleados = () => axiosInstance.get(`/empleados`);
export const getEmpleadoById = (id) => axiosInstance.get(`/empleados/${id}`);
export const search = (texto) => axiosInstance.get(`/search/${texto}`);
export const getEmpleadosByProvincia = (provincia) => axiosInstance.get(`/provincia/${provincia}`);
export const crearEmpleado = (empleadoData) => {return axiosInstance.post(`/crearEmpleado`, empleadoData)};
export const updateEmpleado = (empleadoData) => {return axiosInstance.put(`/updateEmpleado`, empleadoData)};
export const deleteEmpleado = (id) => { return axiosInstance.delete(`/deleteEmpleado/${id}`)};