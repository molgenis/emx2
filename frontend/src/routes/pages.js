import EditPage from '../views/pages/EditPage.vue'
import ListPages from '../views/pages/ListPages.vue'
import ViewPage from '../views/pages/ViewPage.vue'

export default [
  {
    component: ListPages,
    name: 'pages-list',
    path: '/',
    props: true,
  },
  {
    component: ViewPage,
    name: 'pages-view',
    path: '/pages/:page',
    props: true,
  },
  {
    component: EditPage,
    name: 'pages-edit',
    path: '/pages/:page/edit',
    props: true,
  },
]
