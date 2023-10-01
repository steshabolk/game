export const api = {
	gameController: process.env.VUE_APP_API + '/game'
}

export const requests = {
	game: api.gameController,
	recover: api.gameController + '/recover',
	fight: api.gameController + '/fight'
}
