<template>
  <div id="app">
    <Menu />

    <div class="container-fluid">
      <RouterView :key="$route.fullPath" :page="$s.page" :session="$s.session" />
    </div>
    <Footer>
      <span v-if="$s.session && $s.session.manifest">
        Version: <a :href="`https://github.com/molgenis/molgenis-emx2/releases/tag/v${$s.session.manifest.SpecificationVersion}`">
          {{ $s.session.manifest.SpecificationVersion }}
        </a>
      </span>
    </Footer>
  </div>
</template>

<script>
import Footer from '@/components/ui/layout/MolgenisFooter.vue'
import Menu from '@/components/ui/layout/MolgenisMenu.vue'

export default {
  components: {Footer, Menu},
}
</script>
