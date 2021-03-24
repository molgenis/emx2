import ListTables from '@/components/tables/ListTables.vue'
import ViewTable from '@/components/tables/ViewTable.vue'

export default [
  {
    component: ListTables,
    name: 'tables-list',
    path: '/groups/:groupId/tables',
    props: true,
  },
  {
    component: ViewTable,
    name: 'tables-view',
    path: '/groups/:groupId/tables/:table',
    props: true,
  },
]
