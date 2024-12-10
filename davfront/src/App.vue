<template>
  <div id="app">
    <AppMenu @openLogin="openLoginPopup" @openLogout="openLogoutPopup"/>
    <LoginPopup v-if="showLoginPopup" @close="closeLoginPopup" />
    <LogoutPopup v-if="showLogoutPopup" @close="closeLogoutPopup" />
    <router-view />
  </div>
</template>

<script>
import AppMenu from './components/AppMenu.vue';
import LoginPopup from './components/LoginPopup.vue';
import LogoutPopup from './components/LogoutPopup.vue';
import {auth} from "@/firebase";

export default {
  name: 'App',
  components: {
    AppMenu,
    LoginPopup,
    LogoutPopup,
  },
  data() {
    return {
      showLoginPopup: false,
      showLogoutPopup: false,
    };
  },
  methods: {
    closeLoginPopup() {
      this.showLoginPopup = false;
    },
    openLoginPopup() {
      this.showLoginPopup = true;
    },
    openLogoutPopup() {
      this.showLogoutPopup = true;
      this.logout();
      setTimeout(() => {
        this.showLogoutPopup = false;
      }, 3000);
    },
    closeLogoutPopup() {
      this.showLogoutPopup = false;
    },
    logout() {
      auth.signOut().then(() => {
        this.isAuthenticated = false;
        console.log('User logged out');
      }).catch((error) => {
        console.error('Logout error:', error);
      });
    },
  },
};
</script>


<style>
/* Estilos globales */
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
}
</style>
