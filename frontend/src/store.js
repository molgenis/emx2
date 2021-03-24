import {reactive} from 'vue'

const persistant = reactive({
  language: 'en',
  style: {
    logo: '/apps/styleguide/assets/img/molgenis_logo.png',
  },
  title: 'Welcome to EMX2',
})

const volatile = {
  menuItems: [
    {active: true, href: '.', label: 'Databases'},
    {
      href: '/apps/graphql-playground/',
      label: 'GraphQL API',
    },
  ],
  page: null,
  schemas: [],
  session: {},
}
class Store {

  load() {
    let restoredState
    try {
      restoredState = JSON.parse(localStorage.getItem('emx2-store'))
    } catch (err) {
      restoredState = {}
    }

    Object.assign(persistant, {...restoredState, ...volatile})
    return persistant
  }

  save() {
    localStorage.setItem('emx2-store', JSON.stringify(volatile))
  }
}

export default Store
