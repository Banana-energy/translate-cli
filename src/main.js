import Vue from 'vue'
import App from './App.vue'
import store from '@/store'
import router from '@/router'
import ViewUI from 'view-design'
import 'view-design/dist/styles/iview.css'
import '@/assets/styles/index.scss'

Vue.config.productionTip = false

import TableBox from '@/components/TableBox'
Vue.component('TableBox', TableBox)

Vue.use(ViewUI)

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
