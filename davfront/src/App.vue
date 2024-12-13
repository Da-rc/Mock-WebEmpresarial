<template>
  <div id="app">
    <AppMenu @openLogin="openLoginPopup"/>
    <LoginPopup v-if="showLoginPopup" @close="closeLoginPopup" />
    <router-view />
  </div>
</template>

<script>
import AppMenu from './components/AppMenu.vue';
import LoginPopup from './components/LoginPopup.vue';

export default {
  name: 'App',
  components: {
    AppMenu,
    LoginPopup,
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
