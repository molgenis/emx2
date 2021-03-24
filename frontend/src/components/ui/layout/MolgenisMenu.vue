<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-secondary">
    <button
      aria-controls="navbarNav"
      aria-expanded="false"
      aria-label="Toggle navigation"
      class="navbar-toggler"
      data-target="#navbarNav"
      data-toggle="collapse"
      type="button"
    >
      <span class="navbar-toggler-icon" />
    </button>
    <a v-if="logo" class="navbar-brand" href="/">
      <img alt="brand-logo" class="molgenis-navbar-logo" :src="logo">
    </a>
    <div id="navbarNav" class="collapse navbar-collapse">
      <ul v-if="items" class="navbar-nav">
        <li
          v-for="item in items.filter((i) => permitted(i))"
          :key="item.label"
          class="nav-item"
          :class="{
            active: item.active,
            dropdown: item.submenu,
          }"
        >
          <ButtonDropdown
            v-if="item.submenu && item.submenu.length > 0"
            class="nav-item"
            icon="caret-down"
            :label="item.label"
          >
            <a
              v-for="sub in item.submenu"
              :key="sub.label"
              class="dropdown-item"
              :href="sub.href"
              :target="sub.newWindow ? '_blank' : '_self'"
            >{{ sub.label }}</a>
          </ButtonDropdown>
          <a
            v-else
            class="nav-link"
            :href="item.href"
            :target="item.newWindow ? '_blank' : '_self'"
          >{{ item.label }}
          </a>
        </li>
      </ul>
    </div>
    <Session />
  </nav>
</template>

<script>
import ButtonDropdown from '../forms/ButtonDropdown.vue'
import {DefaultMenuMixin} from '@/components/ui/index.js'
import Session from '@/components/ui/layout/MolgenisSession.vue'

/** You can use the slot to put a component in the right of menu, e.g. an 'Account' component */
export default {
  components: {
    ButtonDropdown,
    Session,
  },
  mixins: [DefaultMenuMixin],
  props: {
    /** logo to show*/
    logo: String,
  },
  computed: {
    items() {
      if (this.$s.session && this.$s.session.settings && this.$s.session.settings.menu) {
        return this.$s.session.settings.menu
      } else if (this.$s.menuItems) {
        return this.$s.menuItems
      } else {
        return this.defaultMenu
      }
    },
  },
  methods: {
    permitted(item) {
      if (!item.role) {
        return true
      }
      if (this.$s.session && Array.isArray(this.$s.session.roles)) {
        if (this.$s.session.email == 'admin') {
          return true
        }
        if (item.role == 'Viewer') {
          return this.$s.session.roles.some((r) =>
            ['Viewer', 'Editor', 'Manager', 'Owner'].includes(r),
          )
        } else if (item.role == 'Editor') {
          return this.$s.session.roles.some((r) =>
            [('Editor', 'Manager', 'Owner')].includes(r),
          )
        } else if (item.role == 'Manager') {
          return this.$s.session.roles.some((r) =>
            ['Manager', 'Owner'].includes(r),
          )
        }
      }
      return false
    },
  },
}
</script>
