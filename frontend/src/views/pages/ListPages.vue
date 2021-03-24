<template>
  <div>
    <h1>Page list</h1>
    <table class="table">
      <thead>
        <tr>
          <th>Page</th>
          <th>View</th>
          <th>Edit</th>
        </tr>
      </thead>
      <tr v-for="page in pages">
        <td>{{ page }}</td>
        <td>
          <RouterLink :to="'/' + page">
            view
          </RouterLink>
        </td>
        <td>
          <RouterLink :to="'/' + page + '/edit'">
            edit
          </RouterLink>
        </td>
      </tr>
    </table>

    <ShowMore title="debug">
      <pre>
session = {{ session }}
      </pre>
    </ShowMore>
  </div>
</template>

<script>
import {ShowMore} from '@/components/ui/index.js'

export default {
  components: {
    ShowMore,
  },
  props: {
    session: Object,
  },
  computed: {
    pages() {
      if (this.session && this.session.settings) {
        return Object.keys(this.session.settings)
          .filter(key => key.startsWith('page.'))
          .map(key => key.substring(5))
      } else {
        return undefined
      }
    },
  },
}
</script>
