import instance from '@/store/axiosInstance'
import { requests } from '@/_config'
import { UNKNOWN_ERROR, NO_ACTIVE_GAME, INVALID_DATA, CHECK_HEALTH_MSG } from '@/services/errorMessages'
import { gameState, playerBody } from '@/services/requestBody'

export default {
	namespaced: true,
	state: {
		...gameState()
	},
	actions: {
		async requestGameInfo({ commit, dispatch, state }) {
			if (state.isGameOn == null) {
				await instance
					.get(requests.game, { timeout: 10000 })
					.then(response => {
						commit('setGameInfo', response.data)
					})
					.catch(error => {
						if (error.response && error.response.data && error.response.data.message && NO_ACTIVE_GAME(error.response.data.message)) {
							commit('setIsGameOn', false)
						} else {
							dispatch('request/setErrMsg', UNKNOWN_ERROR, { root: true })
						}
					})
			}
		},
		async startGame({ commit, dispatch, state }) {
			if (!state.playersValidation.some(val => val === false)) {
				dispatch('request/aipRequest', null, { root: true })
				await instance
					.post(requests.game, {
						player: state.player,
						monsters: state.monsters
					})
					.then(response => {
						dispatch('request/setIsRequesting', false, { root: true })
						commit('setGameInfo', response.data)
					})
					.catch(error => {
						dispatch('request/setIsRequesting', false, { root: true })
						if (error.response && error.response.status && error.response.status === 400) {
							dispatch('request/setErrMsg', INVALID_DATA, { root: true })
						} else {
							dispatch('request/setErrMsg', UNKNOWN_ERROR, { root: true })
						}
					})
			}
		},
		async recoverHealth({ commit, dispatch, state, rootGetters }) {
			if (
				state.isGameOn &&
				!rootGetters['request/isRequesting'] &&
				state.player.recovery > 0 &&
				state.player.currHealth < state.player.maxHealth &&
				state.player.currHealth > 0
			) {
				dispatch('request/aipRequest', null, { root: true })
				await instance
					.get(requests.recover)
					.then(response => {
						dispatch('request/setIsRequesting', false, { root: true })
						commit('setPlayer', response.data)
					})
					.catch(error => {
						dispatch('request/setIsRequesting', false, { root: true })
						if (error.response && error.response.data && error.response.data.message && NO_ACTIVE_GAME(error.response.data.message)) {
							commit('setIsGameOn', false)
						} else if (error.response && error.response.data && error.response.data.message && CHECK_HEALTH_MSG(error.response.data.message)) {
							dispatch('request/setErrMsg', error.response.data.message, { root: true })
						} else {
							dispatch('request/setErrMsg', UNKNOWN_ERROR, { root: true })
						}
					})
			}
		},
		async fight({ commit, dispatch, state, rootGetters }) {
			if (state.isGameOn && !rootGetters['request/isRequesting']) {
				dispatch('request/aipRequest', null, { root: true })
				await instance
					.get(requests.fight)
					.then(response => {
						dispatch('request/setIsRequesting', false, { root: true })
						commit('setGameInfo', response.data)
					})
					.catch(error => {
						dispatch('request/setIsRequesting', false, { root: true })
						if (error.response && error.response.data && error.response.data.message && NO_ACTIVE_GAME(error.response.data.message)) {
							commit('setIsGameOn', false)
						} else {
							dispatch('request/setErrMsg', UNKNOWN_ERROR, { root: true })
						}
					})
			}
		},
		async setPlayer({ commit }, payload) {
			commit('setPlayer', payload)
		},
		async setMonster({ commit }, { monster, idx }) {
			commit('setMonster', { monster, idx })
		},
		async addMonster({ commit }) {
			commit('addMonster')
		},
		async removeMonster({ commit }) {
			commit('removeMonster')
		},
		async setPlayersValidation({ commit }, { status, idx }) {
			commit('setPlayersValidation', { status, idx })
		},
		async initGame({ commit }) {
			commit('initGame')
		}
	},
	getters: {
		getGame(state) {
			return state
		},
		getPlayer(state) {
			return state.player
		},
		getMonsters(state) {
			return state.monsters
		},
		getCurrMonster(state) {
			return state.currMonster
		},
		isGameOn(state) {
			return state.isGameOn
		}
	},
	mutations: {
		initGame(state, payload) {
			Object.assign(state, gameState())
			state.isGameOn = false
		},
		setGameInfo(state, payload) {
			state.isGameOn = payload.isGameOn
			state.round = payload.round
			state.isAttackSuccessful = payload.isAttackSuccessful
			state.damage = payload.damage
			state.player = payload.player
			state.monsters = payload.monsters
			state.currMonster = payload.currMonster
		},
		setIsGameOn(state, payload) {
			state.isGameOn = payload
		},
		setPlayer(state, payload) {
			state.player = payload
		},
		setMonster(state, { monster, idx }) {
			state.monsters[idx] = monster
		},
		addMonster(state, payload) {
			state.monsters.push(playerBody())
			state.playersValidation.push(true)
		},
		removeMonster(state, payload) {
			state.monsters.pop()
			state.playersValidation.pop()
		},
		setPlayersValidation(state, { status, idx }) {
			state.playersValidation[idx] = status
		}
	}
}
