<template>
  <div>
    <h3>Empleados</h3>
    <div class="divBusqueda">
      <input
          type="text"
             id="barraBusqueda"
             @input="buscarEmpleados"
             placeholder="Buscar empleados por nombre, DNI, etc."
             v-model="txtBusqueda" />
    </div>
    <div class="tabla-wrapper">
      <table class="tabla-responsive">
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
            <button @click="openPopupEditar(empleado)">
              <i class="pi pi-pen-to-square" />
            </button>
            <button @click="borrarEmpleado(empleado)">
              <i class="pi pi-trash" />
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <button @click="openPopupCrear" class="add-button">
      <i class="pi pi-plus-circle" />
      Añadir empleado
    </button>
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
import {deleteEmpleado, getAllEmpleados, search} from '@/axios';
import CrearEmpleadoPopup from '../components/CrearEmpleadoPopup.vue';
import UpdateEmpleadoPopup from "@/components/UpdateEmpleadoPopup.vue";
import Swal from "sweetalert2";
import 'primeicons/primeicons.css'




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
      txtBusqueda: null,
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
    buscarEmpleados(){
      if (this.txtBusqueda === ''){
        this.fetchEmpleados();
      } else {
        search(this.txtBusqueda).then((response) => {
          this.empleados = response.data;
        })
      }
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

h3 {
  color: #2A3D66;
}

.divBusqueda input {
  padding: 8px 12px;
  margin-bottom: 20px;
  width: 100%;
  max-width: 300px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.tabla-wrapper {
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;
}

.tabla-responsive {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 12px 15px;
  text-align: left;
  border: 1px solid #ddd;
}

th {
  color: #ddd;
  background-color: #2A3D66;
}

tbody tr {
  cursor: pointer;
}

tbody tr:hover {
  background-color: #f9f9f9;
}

button {
  background-color: transparent;
  color: #2A3D66;
  border: none;
  cursor: pointer;
  font-size: 20px;
  margin-right: 8px;
}


button:hover {
  background-color: #f2994a;
}

.add-button {
  background-color: #2A3D66;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
  margin-left: auto;
  display: block;
}

.add-button:hover {
  background-color: #333;
}

/*responsive*/
@media (max-width: 768px) {
  h3 {
    font-size: 1.5rem;
  }

  table {
    font-size: 14px;
  }

  th, td {
    padding: 8px 12px;
  }

  .divBusqueda input {
    width: 100%;
    padding: 12px;
  }

  .add-button {
    width: 100%;
    padding: 12px;
  }

  button {
    width: 100%;
    padding: 12px;
    margin-bottom: 10px;
  }
}

@media (max-width: 480px) {
  .tabla-wrapper table {
    font-size: 12px;
  }

  .divBusqueda input {
    font-size: 14px;
  }

  button {
    padding: 8px;
    font-size: 12px;
  }
}
</style>