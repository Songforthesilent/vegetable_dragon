import './assets/common.css'
<<<<<<< HEAD
=======

>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
<<<<<<< HEAD
import { createPinia } from 'pinia';

const pinia = createPinia()
const app = createApp(App)

app.config.globalProperties.$axios = axios;  //전역변수로 설정 컴포넌트에서 this.$axios 호출할 수 있음
// app.config.globalProperties.$serverUrl = '//localhost:8081' //api server
app.use(router)
app.use(pinia)
app.mount('#app')
=======

const app = createApp(App)
app.config.globalProperties.$axios = axios;  //전역변수로 설정 컴포넌트에서 this.$axios 호출할 수 있음
app.config.globalProperties.$serverUrl = '//localhost:8081' //api server
app.use(router).mount('#app')
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
