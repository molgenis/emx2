<template>
  <div>
    <RouterLink v-if="schema" to="/">
      Back to {{ schema.name }}
    </RouterLink>
    <TableExplorer :table="table" />
  </div>
</template>

<script>
import {TableExplorer} from '@/components/ui/index.js'

export default {
  components: {
    TableExplorer,
  },
  props: {
    schema: Object,
    table: String,
  },
}
</script>
