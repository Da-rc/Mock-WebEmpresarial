<template>
  <div>
    <h3>Empleados</h3>
    <div class="divBusqueda">
      <i id="icono-lupa" class="pi pi-search" />
      <input
          type="text"
             id="barraBusqueda"
             @input="buscarEmpleados"
             placeholder="Buscar empleados por nombre, DNI, etc."
             v-model="txtBusqueda" />
    </div>

    <div class="div-topTabla">
    <div class="pagination-controls">
      <label for="itemsPerPage">Empleados por página:</label>
      <select v-model="itemsPerPage" id="itemsPerPage" @change="changePage(1)">
        <option value="5">5</option>
        <option value="10">10</option>
      </select>
    </div>

    <button @click="openPopupCrear" class="add-button">
      <i class="pi pi-plus-circle" />
      Añadir empleado
    </button>
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
        <tr v-for="empleado in paginatedEmpleados" :key="empleado.id">
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

    <div class="pagination">
      <button
          v-for="page in totalPages"
          :key="page"
          @click="changePage(page)"
          :class="{ active: currentPage === page }"
      >
        {{ page }}
      </button>
    </div>

    <!-- popups -->
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
      currentPage: 1,
      itemsPerPage: 5,
      showPopupCrear: false,
      showPopupEditar: false,
      empleadoEditar: null,
      txtBusqueda: null,
    };
  },
  computed: {
    totalPages() {
      console.log("Nuevo valor de itemsPerPage:", this.itemsPerPage);
      return Math.ceil(this.empleados.length / this.itemsPerPage);
    },
    paginatedEmpleados() {
      const startIndex = (this.currentPage - 1) * this.itemsPerPage;
      const endIndex = startIndex + this.itemsPerPage;
      return this.empleados.slice(startIndex, endIndex);
    },
  },
  mounted() {
    this.fetchEmpleados();
  },
  methods: {
    fetchEmpleados() {
      getAllEmpleados().then((response) => {
        this.empleados = response.data.sort((a, b) => {
          return a.apellidos.localeCompare(b.apellidos);
        });
        this.currentPage = 1;
      }).catch(() => {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'Ocurrió un error con la conexión. Por favor, inténtalo nuevamente.'});
      });
    },
    changePage(page) {
      this.currentPage = page;
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
          this.currentPage = 1;
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
                  'success'
              );
              this.fetchEmpleados();
            } else {
              Swal.fire('Error', 'Hubo un problema al eliminar el empleado', 'error');
            }
          }).catch((error) => {
            if (error.response.status === 401) {
              Swal.fire('Warning', 'Debe estar logueado para ejecutar esta acción', 'warning');
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
  font-size: 2rem;
  color: #2A3D66;
  margin-bottom: 15px;
}

#icono-lupa {
  margin-right: 5px;
}

.divBusqueda input {
  padding: 8px 12px;
  margin-bottom: 20px;
  width: 100%;
  max-width: 300px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.div-topTabla{
  display: flex;
  justify-content: space-between;
}

.pagination-controls {
  margin: 10px 0;
  display: flex;
  align-items: center;
  gap: 10px;
}

.pagination-controls label {
  font-size: 1rem;
  font-weight: 600;
}

.pagination-controls select {
  padding: 8px;
  font-size: 1rem;
  margin: 0 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 15px;
}

.pagination button {
  background-color: #f2f2f2;
  border: 1px solid #ddd;
  margin: 0 5px;
  padding: 8px 12px;
  border-radius: 4px;
  cursor: pointer;
}

.pagination button.active {
  background-color: #2A3D66;
  color: white;
  font-weight: bold;
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

  .pagination-controls {
    flex-direction: column;
    align-items: flex-start;
  }

  .pagination-controls select {
    width: 50%;
    margin: 10px 0;
  }

  .pagination-controls label {
    font-size: 0.8rem;
  }

  .pagination {
    align-items: flex-start;
    margin-top: 10px;
  }

  .pagination button {
    width: 25%;
    margin-top: 5px;
  }

  .pagination-controls span {
    margin-top: 5px;
    text-align: left;
  }

  .add-button {
    font-size: 0.9rem;
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
    width: 40%;
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