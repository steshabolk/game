export const UNKNOWN_ERROR = 'An error has occurred. Please try again later'
export const INVALID_DATA = 'Please check that your data is correct'

export const NO_ACTIVE_GAME = msg => {
	return msg.includes('No active game')
}

export const CHECK_HEALTH_MSG = msg => {
	return msg.includes('All recovery options have been used') || msg.includes('Health is already max') || msg.includes('You are dead')
}
