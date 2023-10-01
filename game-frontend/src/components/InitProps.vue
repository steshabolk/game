<template>
	<form @submit.prevent="" autocomplete="off" class="main-form-wrapper">
		<div v-for="(field, prop_key) of fields" :key="prop_key">
			<span class="main-bordered-input__label" :class="{ 'main-bordered-input__label__active': creature[prop_key] || isActive === field.id }">{{
				field.label
			}}</span>
			<div class="main-bordered-input" :class="{ 'main-bordered-input__active': isActive === field.id }">
				<input
					v-model="creature[prop_key]"
					@focus="isActive = field.id"
					@blur="isActive = null"
					:type="field.type"
					:placeholder="field.placeholder"
					:id="field.id"
					:name="field.name" />
			</div>
			<div style="height: 1rem">
				<div v-if="isActive === null && v$.creature[prop_key].$invalid" class="form-error-block">
					<p class="main-error-message form-error-msg" v-for="(error, index) of v$.creature[prop_key].$errors" :key="index">
						{{ error.$message }}
					</p>
				</div>
			</div>
		</div>
	</form>
</template>

<script>
import { setup, isFormValid, range_1_30_validator, range_1_500_validator, range_1_100_validator } from '@/validators/validators'
import { helpers } from '@vuelidate/validators'
import { attackField, defenseField, healthField, minDamageField, maxDamageField } from '@/services/inputFields'
import { playerBody, playerProps } from '@/services/requestBody'
import { mapActions, mapGetters } from 'vuex'

export default {
	setup,
	props: {
		creature_key: {
			type: String,
			required: true
		},
		monster_idx: {
			type: Number,
			required: false
		}
	},
	data() {
		return {
			creature: playerBody(),
			isActive: null,
			fields: playerProps()
		}
	},
	computed: {
		isFormValid
	},
	methods: {
		initFields() {
			this.fields.attack = attackField
			this.fields.defense = defenseField
			this.fields.maxHealth = healthField
			this.fields.minDamage = minDamageField
			this.fields.maxDamage = maxDamageField
		},
		...mapActions('game', ['setPlayer', 'setMonster', 'setPlayersValidation'])
	},
	watch: {
		creature: {
			handler(curr, old) {
				if (this.creature_key == 'player') {
					this.setPlayer(curr)
				}
				if (this.creature_key == 'monster') {
					this.setMonster({ monster: curr, idx: this.monster_idx })
				}
			},
			deep: true
		},
		isFormValid: {
			handler(curr, old) {
				if (curr !== old) {
					if (this.creature_key == 'player') {
						this.setPlayersValidation({ status: curr, idx: 0 })
					}
					if (this.creature_key == 'monster') {
						this.setPlayersValidation({ status: curr, idx: this.monster_idx + 1 })
					}
				}
			}
		}
	},
	beforeMount() {
		this.initFields()
	},
	validations() {
		return {
			creature: {
				attack: range_1_30_validator,
				defense: range_1_30_validator,
				maxHealth: range_1_500_validator,
				minDamage: range_1_100_validator,
				maxDamage: {
					...range_1_100_validator,
					validRange: helpers.withMessage(
						() => '*min <= max',
						value => {
							if (this.creature.minDamage && value) {
								return Number(this.creature.minDamage) <= Number(value)
							} else {
								return true
							}
						}
					)
				}
			}
		}
	}
}
</script>
