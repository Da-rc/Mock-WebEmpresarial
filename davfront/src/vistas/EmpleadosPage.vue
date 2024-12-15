<template>
  <div>
    <h3>Empleados</h3>
    <button @click="openPopupCrear" class="add-button">Añadir Empleado</button>
    <table>
      <thead>
      <tr>
        <th>Nombre</th>
        <th>Apellidos</th>
        <th>DNI</th>
        <th>Área</th>
        <th>Localización Oficina</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="empleado in empleados" :key="empleado.id">
        <td>{{ empleado.nombre }}</td>
        <td>{{ empleado.apellidos }}</td>
        <td>{{ empleado.dni }}</td>
        <td>{{ empleado.area }}</td>
        <td>{{ empleado.oficina && empleado.oficina.provincia ? empleado.oficina.provincia : '' }}</td>
        <td>
          <button @click="openPopupEditar(empleado)">Editar</button>
          <button @click="borrarEmpleado(empleado)">Eliminar</button>
        </td>
      </tr>
      </tbody>
    </table>

    <crear-empleado-popup
        v-if="showPopupCrear"
        :isVisible="showPopupCrear"
        :onClose="closePopupCrear"
        @refresh="fetchEmpleados"
    />
    <UpdateEmpleadoPopup
      v-if="showPopupEditar"
      :isVisible="showPopupEditar"
      :empleado="empleadoEditar"
      :onClose="closePopupEditar"
      @refresh="fetchEmpleados"
    />

  </div>
</template>

<script>
import {deleteEmpleado, getAllEmpleados} from '@/axios';
import CrearEmpleadoPopup from '../components/CrearEmpleadoPopup.vue';
import UpdateEmpleadoPopup from "@/components/UpdateEmpleadoPopup.vue";
import Swal from "sweetalert2";

export default {
  name: 'EmpleadosPage',
  components: {
    CrearEmpleadoPopup,
    UpdateEmpleadoPopup,
  },
  data() {
    return {
      empleados: [],
      showPopupCrear: false,
      showPopupEditar: false,
      isAuthenticated: false,
      empleadoEditar: null,
    };
  },
  mounted() {
    this.fetchEmpleados();
  },
  methods: {
    fetchEmpleados() {
      getAllEmpleados().then((response) => {
        this.empleados = response.data;
      });
    },
    openPopupCrear() {

      this.showPopupCrear = true;
    },
    closePopupCrear() {
      this.showPopupCrear = false;
    },
    openPopupEditar(empleado) {
      this.empleadoEditar = empleado;
      this.showPopupEditar = true;
    },
    closePopupEditar() {
      this.showPopupEditar = false;
    },
    borrarEmpleado(empleado) {
      Swal.fire({
        title: 'Confirmar Eliminación',
        text: `¿Está seguro de que deseas eliminar al empleado ${empleado.nombre} ${empleado.apellidos}?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar',
      }).then((result) => {
        if (result.isConfirmed) {

          deleteEmpleado(empleado.id).then((response) => {
            if (response.status === 200) {
              Swal.fire(
                  'Eliminado',
                  `Empleado ${empleado.nombre} ${empleado.apellidos} eliminado exitosamente`,
                  'sucess'
              );
              this.fetchEmpleados();
            } else {
              Swal.fire('Error', 'Hubo un problema al eliminar el empleado', 'error');
            }
          }).catch((error) => {
            if (error.response.status === 401) {
              Swal.fire('Alert', 'Debe estar logueado para ejecutar esta acción', 'Alert');
            }else {
              console.error("Error eliminando empleado:", error);
              Swal.fire('Error', 'No se pudo eliminar al empleado', 'error');
            }
          });
        }
      })
    }
  },
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 8px 12px;
  text-align: left;
  border: 1px solid #ddd;
}

th {
  background-color: #f4f4f4;
}
</style>