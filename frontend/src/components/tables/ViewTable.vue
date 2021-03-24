<template>
  <div>
    <RouterLink v-if="$s.schema" to="/">
      <Icon class="icon icon-mini" name="Home" /> Back to {{ $s.schema.name }}
    </RouterLink>
    <TableExplorer :group-id="groupId" :table="table" />
  </div>
</template>

<script>
import {TableExplorer} from '@/components/ui/index.js'

export default {
  components: {
    TableExplorer,
  },
  props: {
    groupId: String,
    table: String,
  },
}
</script>
