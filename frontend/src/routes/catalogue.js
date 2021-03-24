import AffiliationView from '@/views/catalogue/AffiliationView.vue'
import CatalogueView from '@/views/catalogue/CatalogueView.vue'
import ContactView from '@/views/catalogue/ContactView.vue'
import DatabankView from '@/views/catalogue/DatabankView.vue'
import DatasourceView from '@/views/catalogue/DatasourceView.vue'
import InstitutionView from '@/views/catalogue/InstitutionView.vue'
import ModelView from '@/views/catalogue/ModelView.vue'
import NetworkView from '@/views/catalogue/NetworkView.vue'
import ReleasesView from '@/views/catalogue/ReleasesView.vue'
import ResourceListView from '@/views/catalogue/ResourceListView.vue'
import StudiesView from '@/views/catalogue/StudiesView.vue'
import TableMappingsView from '@/views/catalogue/TableMappingsView.vue'
import TableView from '@/views/catalogue/TableView.vue'
import VariableMappingsView from '@/views/catalogue/VariableMappingsView.vue'
import VariableView from '@/views/catalogue/VariableView.vue'

export default [
  {
    component: CatalogueView,
    name: 'catalogue',
    path: '/catalogue',
  },
  {
    component: NetworkView ,
    name: 'catalogue-cohorts',
    path: '/catalogue/alt',
  },
  // list views
  {
    component: ResourceListView,
    name: 'catalogue-list',
    path: '/catalogue/list/:tableName',
    props: true,
  },
  {
    component: InstitutionView,
    name: 'catalogue-institutions-view',
    path: '/catalogue/institutions/:acronym',
    props: true,
  },

  {
    component: ReleasesView,
    name: 'catalogue-releases-view',
    path: '/catalogue/releases/:acronym/:version',
    props: true,
  },
  {
    component: DatabankView,
    name: 'catalogue-databanks-view',
    path: '/catalogue/databanks/:acronym',
    props: true,
  },

  {
    component: DatasourceView,
    name: 'catalogue-datasource-view',
    path: '/catalogue/datasources/:acronym',
    props: true,
  },
  {
    component: ModelView,
    name: 'catalogue-models-view',
    path: '/catalogue/models/:acronym',
    props: true,
  },
  {
    component: NetworkView,
    name: 'catalogue-networks-view',
    path: '/catalogue/networks/:acronym',
    props: true,
  },
  {
    component: AffiliationView,
    name: 'catalogue-affiliations',
    path: '/catalogue/affiliations/:acronym',
    props: true,
  },
  {
    component: ContactView,
    name: 'catalogue-contacts-view',
    path: '/catalogue/contacts/:name',
    props: true,
  },
  {
    component: StudiesView,
    name: 'catalogue-studies-view',
    path: '/catalogue/studies/:acronym',
    props: true,
  },
  {
    component: VariableView,
    name: 'catalogue-variables-view',
    path: '/catalogue/variables/:acronym/:version/:table/:name',
    props: true,
  },
  {
    component: TableView,
    name: 'catalogue-tables-view',
    path: '/catalogue/tables/:acronym/:version/:name',
    props: true,
  },
  {
    component: VariableMappingsView,
    name: 'catalogue-variablemappings-view',
    path: '/catalogue/variablemappings/:acronym/:version/:name',
    props: true,
  },
  {
    component: TableMappingsView,
    name: 'catalogue-tablemappings',
    path: '/catalogue/tablemappings/:fromAcronym/:fromVersion/:fromTable/:toAcronym/:toVersion/:toTable',
    props: true,
  },
]
