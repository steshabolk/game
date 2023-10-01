import { helpers } from '@vuelidate/validators'
import useVuelidate from '@vuelidate/core'

export function setup() {
	return {
		v$: useVuelidate({
			$lazy: true,
			$autoDirty: true
		})
	}
}

export function isFormValid() {
	return !this.v$.$invalid
}

export const range_1_30_validator = {
	digitPattern: helpers.withMessage(
		() => '*number',
		value => /^(\d+)?$/.test(value)
	),
	range: helpers.withMessage(
		() => '*range [1-30]',
		value => (value ? Number(value) >= 1 && Number(value) <= 30 : true)
	)
}

export const range_1_500_validator = {
	digitPattern: helpers.withMessage(
		() => '*number',
		value => /^(\d+)?$/.test(value)
	),
	range: helpers.withMessage(
		() => '*range [1-500]',
		value => (value ? Number(value) >= 1 && Number(value) <= 500 : true)
	)
}

export const range_1_100_validator = {
	digitPattern: helpers.withMessage(
		() => '*number',
		value => /^(\d+)?$/.test(value)
	),
	range: helpers.withMessage(
		() => '*range [1-100]',
		value => (value ? Number(value) >= 1 && Number(value) <= 100 : true)
	)
}
