<template>
  <Spinner v-if="loading" />
  <div v-else>
    <div>
      <MessageError v-if="error">
        {{ error }}
      </MessageError>
      <span v-if="$s.session.email && $s.session.email != 'anonymous'">
        <a href="#" @click.prevent="showChangePasswordForm = true">
          Hi {{ $s.session.email }}</a>&nbsp;
        <ChangePasswordForm
          v-if="showChangePasswordForm"
          :error="error"
          @cancel="showChangePasswordForm = false"
        />
        <ButtonAction @click="signout">Sign out</ButtonAction>
      </span>
      <span v-else>
        <ButtonAction @click="showSigninForm = true">Sign in</ButtonAction>
        <SigninForm
          v-if="showSigninForm"
          :error="error"
          @cancel="closeSigninForm"
          @signin="changed"
        />
        <ButtonAlt @click="showSignupForm = true">Sign up</ButtonAlt>
        <SignupForm
          v-if="showSignupForm"
          :error="error"
          @cancel="closeSignupForm"
        />
      </span>
    </div>
  </div>
</template>

<script>
import ButtonAction from '../forms/ButtonAction.vue'
import ButtonAlt from '../forms/ButtonAlt.vue'
import ChangePasswordForm from './MolgenisAccount.vue'
import MessageError from '../forms/MessageError.vue'
import {request} from 'graphql-request'
import SigninForm from './MolgenisSignin.vue'
import SignupForm from './MolgenisSignup.vue'
import Spinner from './Spinner.vue'

/** Element that is supposed to be put in menu holding all controls for user account */
export default {
  components: {
    ButtonAction,
    ButtonAlt,
    ChangePasswordForm,
    MessageError,
    SigninForm,
    SignupForm,
    Spinner,
  },
  data: function() {
    return {
      error: null,
      loading: false,
      showChangePasswordForm: false,
      showSigninForm: false,
      showSignupForm: false,
      version: null,
    }
  },
  watch: {
    email() {
      this.showSigninForm = false
      this.showSignupForm = false
    },
  },
  created() {
    this.reload()
  },
  methods: {
    changed() {
      this.reload()
      this.showSigninForm = false
    },
    closeSigninForm() {
      this.showSigninForm = false
      this.error = null
    },
    closeSignupForm() {
      this.showSignupForm = false
      this.error = null
    },
    parseJson(value) {
      try {
        return JSON.parse(value)
      } catch (e) {
        this.error = 'Parsing of settings failed: ' + e + '. value: ' + value
        return null
      }
    },
    async reload() {
      this.loading = true
      let data
      try {
        data = await request('/graphql',
          '{_session{email,roles},_settings{key,value},_manifest{ImplementationVersion,SpecificationVersion}}',
        )
      } catch (error) {
        if (error.response.status === 504) {
          this.error = 'Error. Server cannot be reached.'
        } else {
          this.error = 'internal server error ' + error
        }
        this.loading = false
      }

      if (data._session != undefined) {
        this.$s.session = data._session
      } else {
        this.$s.session = {}
      }
      // convert settings to object
      this.$s.session.settings = {}
      data._settings.forEach(
        (s) =>
          (this.$s.session.settings[s.key] =
            s.value.startsWith('[') || s.value.startsWith('{')
              ? this.parseJson(s.value)
              : s.value),
      )
      this.$s.session.manifest = data._manifest
      this.loading = false
    },
    signout() {
      this.loading = true
      this.showSigninForm = false
      request('/graphql', 'mutation{signout{status}}')
        .then((data) => {
          if (data.signout.status === 'SUCCESS') {
            this.$s.session = {}
          } else {
            this.error = 'sign out failed'
          }
          this.loading = false
          this.reload()
        })
        .catch((error) => (this.error = 'internal server error' + error))
    },
  },
}
</script>
