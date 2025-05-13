<template>
  <div class="menu">
      <nav>
        <button class="hamburger" @click="toggleMenu">
          <span></span>
          <span></span>
          <span></span>
        </button>
        <ul :class="{ 'menu-open': menuOpen }">
          <li><router-link to="/" @click="closeMenu">Dashboard</router-link></li>
          <li><router-link to="/oficinas" @click="closeMenu">Oficinas</router-link></li>
          <li><router-link to="/empleados" @click="closeMenu">Empleados</router-link></li>
        </ul>
      </nav>
    <div class="auth-buttons">
        <a href="https://github.com/Da-rc/Mock-WebEmpresarial" target="_blank"><i class="pi pi-github" style="color: white"/></a>
      <button v-if="!isAuthenticated" @click="openLoginPopup">Login</button>
      <button v-else @click="logout">Logout</button>
    </div>
  </div>
</template>

<script>
import { auth } from '@/firebase';
import Swal from "sweetalert2";

export default {
  name: 'AppMenu',
  data() {
    return {
      isAuthenticated: false,
      menuOpen: false,
    };
  },
  methods: {
    openLoginPopup() {
      this.$emit('openLogin');
    },
    logout() {
      auth.signOut().then(() => {
        this.isAuthenticated = false;
        Swal.fire(
            'Logout',
            `Se ha desconectado correctamente`,
            'sucess'
        );
      }).catch((error) => {
        console.error('Logout error:', error);
      });
    },
    toggleMenu() {
      this.menuOpen = !this.menuOpen;
    },
    closeMenu() {
      this.menuOpen = false;
    }
  },
  created() {
    auth.onAuthStateChanged((user) => {
     this.isAuthenticated = !!user;
    });
  },
};
</script>



<style scoped>
.menu {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #4a4a4a;
  padding: 10px 20px;
  color: white;
  position: relative;
}

nav {
  display: flex;
  align-items: center;
}

ul {
  list-style: none;
  display: flex;
  gap: 20px;
  margin: 0;
  padding: 0;
}

li a {
  color: white;
  text-decoration: none;
  font-weight: bold;
  transition: color 0.3s;
}

li a:hover {
  color: #f2a365;
}

.auth-buttons {
    display: flex;
    justify-content: space-around;
    align-items: center;
}

.auth-buttons a {
    padding: 5px 10px;
}

.auth-buttons button{
  background: transparent;
  color: white;
  border: 1px solid white;
  border-radius: 4px;
  padding: 5px 10px;
  cursor: pointer;
  transition: background 0.3s, color 0.3s;
}

.auth-buttons button:hover {
  background: #f2a365;
  color: #333;
}

.hamburger {
  display: none;
  flex-direction: column;
  cursor: pointer;
  background: transparent;
  border: none;
}

.hamburger span {
  display: block;
  width: 25px;
  height: 3px;
  margin: 3px;
  background-color: white;
  transition: all 0.3s ease;
}

/* responsive */
@media (max-width: 768px) {
  .hamburger {
    display: flex;
  }

  ul {
    display: none;
    flex-direction: column;
    position: absolute;
    top: 50px;
    left: 0;
    background-color: #4a4a4a;
    width: 25%;
    padding: 10px ;
    text-align: left;
  }

  ul.menu-open {
    display: flex;
  }

  .auth-buttons button {
    background: transparent;
    color: white;
    border: 1px solid white;
    border-radius: 4px;
    padding: 5px 10px;
    cursor: pointer;
    transition: background 0.3s, color 0.3s;
  }

  .auth-buttons button:hover {
    background: #f2a365;
    color: #333;
  }
}
</style>