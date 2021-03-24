<template>
  <Spinner v-if="loading" />
  <div v-else>
    <MessageError v-if="graphqlError">
      {{ graphqlError }}
    </MessageError>
    <IconBar>
      <label>{{ count }} databases found</label>
      <IconAction
        v-if="$s.session && $s.session.email == 'admin'"
        icon="plus"
        @click="openCreateSchema"
      />
    </IconBar>
    <TableSimple
      :columns="['name', 'description']"
      :rows="$s.schemas"
      @click="openGroup"
    >
      <template #rowheader="slotProps">
        <IconDanger
          v-if="$s.session && $s.session.email == 'admin'"
          :key="slotProps.row.name"
          icon="trash"
          @click="openDeleteSchema(slotProps.row.name)"
        />
      </template>
    </TableSimple>
    <SchemaCreateModal v-if="showCreateSchema" @close="closeCreateSchema" />
    <SchemaDeleteModal
      v-if="showDeleteSchema"
      :schema-name="showDeleteSchema"
      @close="closeDeleteSchema"
    />
    <ShowMore
      title="debug"
    >
      session = {{ $s.session }}, schemas = {{ $s.schemas }}
    </ShowMore>
  </div>
</template>

<script>
import MessageError from '@/components/ui/forms/MessageError.vue'
import {request} from 'graphql-request'
import SchemaCreateModal from '@/components/central/SchemaCreateModal.vue'
import SchemaDeleteModal from '@/components/central/SchemaDeleteModal.vue'
import {IconAction, IconBar, IconDanger, ShowMore, Spinner, TableSimple} from '@/components/ui/index.js'

export default {
  components: {
    IconAction,
    IconBar,
    IconDanger,
    MessageError,
    SchemaCreateModal,
    SchemaDeleteModal,
    ShowMore,
    Spinner,
    TableSimple,
  },
  data: function() {
    return {
      graphqlError: null,
      loading: false,
      showCreateSchema: false,
      showDeleteSchema: false,
    }
  },
  computed: {
    count() {
      return this.$s.schemas.length
    },
  },
  created() {
    this.getSchemaList()
  },
  methods: {
    closeCreateSchema() {
      this.showCreateSchema = false
      this.getSchemaList()
    },
    closeDeleteSchema() {
      this.showDeleteSchema = null
      this.getSchemaList()
    },
    async getSchemaList() {
      this.loading = true
      let data
      try {
        data = await request('/graphql', '{Schemas{name}}')
      } catch(error) {
        this.graphqlError = 'internal server graphqlError' + error
      }

      this.$s.schemas = data.Schemas
      this.loading = false
    },
    openCreateSchema() {
      this.showCreateSchema = true
    },
    openDeleteSchema(schemaName) {
      this.showDeleteSchema = schemaName
    },
    openGroup(group) {
      this.$router.push({name: 'tables-list', params: {groupId: group.name}})
    },
  },
}
</script>
