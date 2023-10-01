<template>
	<div class="wrapper">
		<div class="wrapper-content">
			<div class="container">
				<div v-if="this.game.isGameOn === null && errMsg" class="par" style="height: 100vh; padding: 45vh 3rem 0">{{ errMsg }}</div>
				<div v-if="this.game.isGameOn === false && this.game.round === 0">
					<p class="par" style="margin-top: 1.5rem">no active game</p>
					<p class="par">create players</p>
					<p class="text" style="text-align: center">* blank fields will be set randomly</p>
					<p class="par" style="margin: 1rem auto 0.5rem">player</p>
					<InitProps :creature_key="'player'" />

					<div class="inline" style="margin: 0.5rem auto">
						<SvgIcon
							@click="changeMonstersCount(-1)"
							class="main-btn gr-purple-blue"
							:class="{ 'btn-disable': monstersCount === 1 }"
							:viewBox="minusIcon.viewBox"
							:path="minusIcon.svgPath" />
						<p class="par" style="margin: 0 0.5rem 0">monsters</p>
						<SvgIcon
							@click="changeMonstersCount(1)"
							class="main-btn gr-purple-pink"
							:class="{ 'btn-disable': monstersCount === 5 }"
							:viewBox="plusIcon.viewBox"
							:path="plusIcon.svgPath" />
					</div>
					<div v-for="idx in monstersCount" :key="idx">
						<InitProps :creature_key="'monster'" :monster_idx="idx - 1" />
					</div>
					<button @click="startGame()" class="main-btn" style="margin-top: 0.5rem">start</button>
					<p v-if="errMsg" class="text" style="margin-top: 1rem">{{ errMsg }}</p>
				</div>

				<div
					v-if="this.game.isGameOn || (this.game.isGameOn === false && this.game.round > 0)"
					class="grid grid-col"
					style="min-height: 100vh; margin: 0 1rem">
					<div class="grid-column">
						<PlayerStatus :creature_key="'player'" />
					</div>
					<div class="grid-column grid-w" style="gap: 0">
						<div style="margin: 0 auto 0.5rem; display: flex; align-items: center">
							<SvgIcon
								class="icon-yellow-red"
								style="width: 1.5rem; height: 1.5rem; margin-right: 0.5rem"
								:viewBox="fightIcon.viewBox"
								:path="fightIcon.svgPath" />
							<p v-if="game.round > 1 || !this.game.isGameOn" class="par text-yellow-red">{{ roundResult }}</p>
						</div>
						<p class="par">round : {{ this.game.round }}</p>
						<button v-if="this.game.isGameOn" @click="fight()" class="main-btn" style="margin-top: 0.5rem">fight</button>
						<p v-if="!this.game.isGameOn" class="par">the game is over</p>
						<p v-if="!this.game.isGameOn" class="par">{{ winner }}</p>
						<p v-if="errMsg" class="text" style="margin-top: 1rem">{{ errMsg }}</p>
						<button v-if="!this.game.isGameOn" @click="initGame()" class="main-btn" style="margin-top: 0.5rem">new game</button>
					</div>
					<div class="grid-column grid-row">
						<div v-for="idx in monsters.length" :key="idx">
							<PlayerStatus :creature_key="'monster'" :monster_idx="idx - 1" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import InitProps from '@/components/InitProps'
import PlayerStatus from '@/components/PlayerStatus'
import SvgIcon from '@/components/SvgIcon.vue'
import { plusIcon, minusIcon, fightIcon } from '@/services/svgIcons'

export default {
	name: 'App',
	components: {
		InitProps,
		SvgIcon,
		PlayerStatus
	},
	data() {
		return {
			plusIcon,
			minusIcon,
			fightIcon
		}
	},
	methods: {
		changeMonstersCount(n) {
			if (n < 0 && this.monstersCount > 1) {
				this.removeMonster()
			}
			if (n > 0 && this.monstersCount < 5) {
				this.addMonster()
			}
		},
		...mapActions('request', ['clearErrMsg']),
		...mapActions('game', ['requestGameInfo', 'addMonster', 'removeMonster', 'startGame', 'fight', 'initGame'])
	},
	computed: {
		monstersCount() {
			return this.monsters.length
		},
		roundResult() {
			return this.game.isAttackSuccessful ? this.game.damage : 'failure'
		},
		winner() {
			if (this.game.player.currHealth > 0) {
				return 'You have won!'
			} else {
				return 'The ' + this.game.monsters.length === 1 ? 'monster has' : 'monsters have' + ' won!'
			}
		},
		...mapGetters('request', { errMsg: 'getErrMsg' }),
		...mapGetters('game', { game: 'getGame', monsters: 'getMonsters' })
	},
	beforeMount() {
		this.clearErrMsg()
		this.requestGameInfo()
	}
}
</script>
