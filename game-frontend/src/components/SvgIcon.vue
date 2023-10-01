<template>
	<svg class="svg-icon" role="presentation" aria-hidden="true" :viewBox="viewBox">
		<path :d="path" />
		<linearGradient gradientTransform="rotate(120, 0.5, 0.5)" x1="50%" y1="0%" x2="50%" y2="100%" id="purple-pink">
			<stop offset="0%" stop-color="#743ad5" />
			<stop offset="70%" stop-color="#d53a9d" />
		</linearGradient>
		<linearGradient gradientTransform="rotate(120, 0.5, 0.5)" x1="50%" y1="0%" x2="50%" y2="100%" id="purple-blue">
			<stop offset="0%" stop-color="#743ad5" />
			<stop offset="100%" stop-color="#3ad5d4" />
		</linearGradient>
		<linearGradient gradientTransform="rotate(120, 0.5, 0.5)" x1="50%" y1="0%" x2="50%" y2="100%" id="yellow-red">
			<stop offset="0%" stop-color="#fd1d1d" />
			<stop offset="100%" stop-color="#fcb045" />
		</linearGradient>
	</svg>
</template>

<script>
export default {
	props: {
		viewBox: {
			type: String,
			required: true
		},
		path: {
			type: String,
			required: true
		}
	}
}
</script>
