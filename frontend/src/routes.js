import catalogueRoutes from './routes/catalogue.js'
import centralRoutes from './routes/central.js'
import pagesRoutes from './routes/pages.js'
import schemaRoutes from './routes/schema.js'
import settingsRoutes from './routes/settings.js'
import tablesRoutes from './routes/tables.js'

export default [
  ...catalogueRoutes,
  ...centralRoutes,
  ...pagesRoutes,
  ...schemaRoutes,
  ...tablesRoutes,
  ...settingsRoutes,
]
