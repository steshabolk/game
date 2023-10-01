// REQUEST BODY
export function playerBody() {
	return {
		attack: null,
		defense: null,
		maxHealth: null,
		currHealth: null,
		minDamage: null,
		maxDamage: null
	}
}

export function playerProps() {
	return {
		attack: null,
		defense: null,
		maxHealth: null,
		minDamage: null,
		maxDamage: null
	}
}

export function gameBody() {
	return {
		isGameOn: null,
		round: 0,
		isAttackSuccessful: null,
		damage: null,
		player: playerBody(),
		monsters: [playerBody()],
		currMonster: null
	}
}

export function gameState() {
	return {
		...gameBody(),
		playersValidation: [true, true]
	}
}
