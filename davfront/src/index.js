import { createRouter, createWebHistory } from 'vue-router';
import DashboardMain from './vistas/DashboardMain.vue';
import OficinasPage from './vistas/OficinasPage.vue';
import EmpleadosPage from './vistas/EmpleadosPage.vue'; // Verifica esta línea

const routes = [
    {
        path: '/',
        name: 'Dashboard',
        component: DashboardMain,
    },
    {
        path: '/oficinas',
        name: 'Oficinas',
        component: OficinasPage,
    },
    {
        path: '/empleados',
        name: 'Empleados',
        component: EmpleadosPage,
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;
