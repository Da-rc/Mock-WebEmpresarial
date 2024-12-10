<template>
  <div class="menu">
      <nav>
        <ul>
          <li><router-link to="/">Dashboard</router-link></li>
          <li><router-link to="/oficinas">Oficinas</router-link></li>
          <li><router-link to="/empleados">Empleados</router-link></li>
        </ul>
      </nav>

    <button v-if="!isAuthenticated" @click="openLoginPopup">Login</button>
    <button v-else @click="logout">Logout</button>
  </div>
</template>

<script>
import { auth } from '@/firebase';

export default {
  name: 'AppMenu',
  data() {
    return {
      isAuthenticated: false,  // Estado para verificar si el usuario está autenticado
    };
  },
  methods: {
    openLoginPopup() {
      this.$emit('openLogin');
    },
    logout() {
      this.$emit('openLogout');
    },
  },
  created() {
    auth.onAuthStateChanged((user) => {
      if (user) {
        this.isAuthenticated = true;
      } else {
        this.isAuthenticated = false;
      }
    });
  },
};
</script>



<style scoped>
.menu {
  background-color: #6c4675;
  padding: 10px;
  color: white;
}

.menu ul {
  list-style: none;
  display: flex;
  justify-content: space-around;
  padding: 0;
  margin: 0;
}

.menu li a {
  color: white;
  text-decoration: none;
}

.menu li a:hover {
  text-decoration: underline;
}

.menu button {
  background-color: transparent;
  border: none;
  color: white;
  cursor: pointer;
}

.menu button:hover {
  text-decoration: underline;
}
</style>