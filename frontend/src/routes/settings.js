import Layout from '@/components/settings/Layout.vue'
import Members from '@/components/settings/Members.vue'
import MenuManager from '@/components/settings/MenuManager.vue'
import PageManager from '@/components/settings/PageManager.vue'

export default [
  {
    component: Layout,
    name: 'settings-layout',
    path: '/settings/layout',
  },
  {
    component: Members,
    name: 'settings-members',
    path: '/settings/members',
  },
  {
    component: MenuManager,
    name: 'settings-menu',
    path: '/settings/menu',
  },
  {
    component: PageManager,
    name: 'settings-pages',
    path: '/settings/pages',
  },
]

