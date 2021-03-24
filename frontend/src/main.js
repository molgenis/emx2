import './css/emx2.css'
import App from './App.vue'
import {createApp} from 'vue'
import {createI18n} from 'vue-i18n'
import draggable from 'vuedraggable'
import Icon from './components/ui/icons/Icon.vue'
import localeNL from './locales/nl.js'
import routes from './routes.js'
import Store from './store.js'
import {createRouter, createWebHistory} from 'vue-router'

const app = globalThis.app = {}
app.store = new Store()

app.vm = createApp(App)
app.state = app.store.load()
app.vm.config.globalProperties.$s = app.state

app.i18n = createI18n({
  locale: app.state.language,
  messages: {
    nl: localeNL,
  },
  silentFallbackWarn: true,
  silentTranslationWarn: true,
})

app.vm.component('Icon', Icon)
app.vm.component('Draggable', draggable)

app.vm.directive('click-outside', {
  beforeMount(el, binding) {
    el.clickOutsideEvent = function(event) {
      if (!(el === event.target || el.contains(event.target))) {
        binding.value(event, el)
      }
    }
    document.body.addEventListener('click', el.clickOutsideEvent)
  },
  unmounted(el) {
    document.body.removeEventListener('click', el.clickOutsideEvent)
  },
})

app.router = createRouter({
  history: createWebHistory(),
  linkActiveClass: 'active',
  routes,
})

app.vm.use(app.router).use(app.i18n)
app.vm.mount('#app')
