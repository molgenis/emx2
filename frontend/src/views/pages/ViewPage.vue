<template>
  <div>
    <RouterLink
      v-if="canEdit" :to="'/' + page + '/edit'"
    >
      edit page
    </RouterLink>
    <div v-html="contents" />
  </div>
</template>

<script>
export default {
  props: {
    page: String,
    session: Object,
  },
  computed: {
    canEdit() {
      return (
        this.session &&
        (this.session.email == 'admin' ||
          (this.session.roles && this.session.roles.includes('Manager')))
      )
    },
    contents() {
      if (
        this.session &&
        this.session.settings &&
        this.session.settings['page.' + this.page]
      ) {
        return this.session.settings['page.' + this.page]
      }
      return 'Page not found'
    },
  },
}
</script>
