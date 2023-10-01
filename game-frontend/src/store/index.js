import { createStore } from 'vuex'

import request from '@/store/request'
import game from '@/store/game'

const store = {
	modules: {
		request,
		game
	}
}

export default createStore(store)
