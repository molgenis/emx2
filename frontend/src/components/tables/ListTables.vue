<template>
  <div v-if="$s.schema">
    <h1>Tables in '{{ $s.schema.name }}'</h1>

    Download all tables:
    <a href="../api/zip">zip</a> | <a href="../api/excel">excel</a> |
    <a href="../api/jsonld">jsonld</a> | <a href="../api/ttl">ttl</a><br>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">
            Table
            <div class="form-check form-check-inline">
              <InputCheckbox
                v-model="tableFilter"
                class="ml-2"
                :clear="false"
                :default-value="tableFilter"
                :options="['external']"
              />
            </div>
          </th>
          <th v-if="tableFilter.includes('external')" scope="col">
            externalSchema
          </th>
          <th scope="col">
            Description
          </th>
        </tr>
      </thead>
      <tr
        v-for="table in $s.schema.tables.filter(
          (table) =>
            table.externalSchema == undefined ||
            tableFilter.includes('external')
        )"
        :key="table.name"
      >
        <td>
          <RouterLink :to="{name: 'tables-view', params: {groupId, table: table.name}}">
            {{ table.name }}
          </RouterLink>
        </td>
        <td v-if="tableFilter.includes('external')">
          {{ table.externalSchema }}
        </td>
        <td>{{ table.description }}</td>
      </tr>
    </table>
  </div>
  <MessageError v-else>
    No tables found. Might you need to login?
  </MessageError>
</template>

<script>
import {request} from 'graphql-request'
import {InputCheckbox, MessageError} from '@/components/ui/index.js'

export default {
  components: {
    InputCheckbox,
    MessageError,
  },
  props: {
    groupId: String,
    schema: Object,
  },
  data() {
    return {
      tableFilter: [],
    }
  },
  computed: {
    count() {
      if (!this.schema || !this.schema.tables) {
        return 0
      }
      return this.schema.tables.length
    },
  },
  created() {
    this.loadSchema()
  },
  methods: {
    async loadSchema() {
      this.loading = true
      let data
      try {
        data = await request(
          `/${this.groupId}/tables/graphql`,
          '{_schema{name,tables{name,externalSchema,description,columns{name,columnType,key,refTable,required,description}}}}',
        )
        this.$s.schema  = data._schema
      } catch(error) {
        if (error.response.error.status === 403) {
          this.graphqlError = 'Forbidden. Do you need to login?'
        } else this.graphqlError = error.response.error
      } finally {
        this.loading = false
      }
    },
  },
}
</script>
