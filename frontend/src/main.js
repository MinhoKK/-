import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import App from './App.vue'
import router from './router'

//import './style.css'

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap'
import './assets/style.css'

const app = createApp(App)
const pinia = createPinia()

pinia.use(piniaPluginPersistedstate)

app.use(pinia)
app.use(router)

router.isReady().then(() => {
  app.mount('#app')
})
