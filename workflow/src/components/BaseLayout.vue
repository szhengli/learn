<template>
<div> 

	<main>
		<slot :user="user" >
			{{ user.firstname }}
		</slot>
	</main>
	<footer>
		....................
			<slot name="footer">
				
			</slot>
	</footer>


</div>
</template>

<script>
	export default {
		data: function(){
			return {
				user: {
					firstname: "james",
					lastname: "zheng"
				}
			}
		}
	}
</script>

<style>
</style>
