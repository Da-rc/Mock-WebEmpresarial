import { createApp } from 'vue'
import App from './App.vue'
import router from './index';
import { OhVueIcon, addIcons } from "oh-vue-icons";
import { FaRegularEdit } from "oh-vue-icons/icons";

addIcons(FaRegularEdit);

const app = createApp(App);

app.component("v-icon", OhVueIcon);

app.use(router).mount('#app')
