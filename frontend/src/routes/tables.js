import ListTables from '@/components/tables/ListTables.vue'
import ViewTable from '@/components/tables/ViewTable.vue'

export default [
  {
    component: ListTables,
    name: 'tables-list',
    path: '/tables',
    props: true,
  },
  {
    component: ViewTable,
    name: 'tables-view',
    path: '/tables/:table',
    props: true,
  },
]
