<template>
	<div class="grid grid-center">
		<div class="grid-column">
			<SvgIcon
				:class="{ 'icon-purple-pink': getPlayer.currHealth > 0 }"
				v-if="creature_key === 'player'"
				:viewBox="warriorIcon.viewBox"
				:path="warriorIcon.svgPath" />
			<SvgIcon
				:class="{ 'icon-purple-blue': getPlayer.currHealth > 0 }"
				v-if="creature_key === 'monster'"
				:viewBox="monstersIcons[monster_idx].viewBox"
				:path="monstersIcons[monster_idx].svgPath" />
		</div>
		<div class="grid-column text" style="gap: 0">
			<p
				:class="{
					'text-pink-red':
						(creature_key === 'player' && getPlayer.isTurn) || (creature_key === 'monster' && monster_idx == currMonster && getPlayer.isTurn)
				}">
				attack : {{ getPlayer.attack }}
			</p>
			<p
				:class="{
					'text-pink-red':
						(creature_key === 'player' && !getPlayer.isTurn) || (creature_key === 'monster' && monster_idx == currMonster && !getPlayer.isTurn)
				}">
				defense : {{ getPlayer.defense }}
			</p>
			<p>
				health :
				<span
					:class="{
						'text-purple-pink': creature_key === 'player' && getPlayer.currHealth > 0,
						'text-purple-blue': creature_key === 'monster' && getPlayer.currHealth > 0
					}"
					>{{ getPlayer.currHealth }}</span
				>
				/ {{ getPlayer.maxHealth }}
			</p>
			<p>damage : {{ getPlayer.minDamage }} - {{ getPlayer.maxDamage }}</p>
			<p v-if="creature_key === 'player'">recovery : {{ getPlayer.recovery }}</p>
			<button
				v-if="creature_key === 'player'"
				@click="recoverHealth()"
				class="main-btn"
				:class="{ 'btn-disable': !isRecoverPossible }"
				style="margin: 0.3rem auto 0 0">
				recover
			</button>
		</div>
	</div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import SvgIcon from '@/components/SvgIcon.vue'
import { warriorIcon, monsterIcon, deathIcon, vampIcon, orcIcon, zombieIcon } from '@/services/svgIcons'

export default {
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
	components: {
		SvgIcon
	},
	data() {
		return {
			warriorIcon,
			monstersIcons: [monsterIcon, vampIcon, orcIcon, zombieIcon, deathIcon]
		}
	},
	computed: {
		getPlayer() {
			if (this.creature_key == 'player') {
				return this.player
			}
			if (this.creature_key == 'monster') {
				return this.monsters[this.monster_idx]
			}
			return null
		},
		isRecoverPossible() {
			return (
				this.creature_key == 'player' &&
				this.isGameOn &&
				this.player.recovery > 0 &&
				this.player.currHealth < this.player.maxHealth &&
				this.player.currHealth > 0
			)
		},
		...mapGetters('game', { isGameOn: 'isGameOn', player: 'getPlayer', monsters: 'getMonsters', currMonster: 'getCurrMonster' })
	},
	methods: {
		...mapActions('game', ['recoverHealth'])
	}
}
</script>
