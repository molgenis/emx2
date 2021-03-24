<template>
  <FormGroup
    :id="id" :help="help"
    :label="label"
    :placeholder="placeholder"
  >
    <input
      :id="id"
      v-model="valueArray[0]"
      :aria-describedby="id + 'Help'"
      class="form-control"
      :placeholder="placeholder"
      type="password"
      @input="emitValue"
    >
  </FormGroup>
</template>

<script>
import _baseInput from './_baseInput.vue'
import FormGroup from './_formGroup.vue'

/** Input for passwords */
export default {
  components: {FormGroup},
  extends: _baseInput,
}
</script>
