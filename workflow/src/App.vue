<template>
	
	<div> 
	
	<button @click="bshow" > test</button>
	<router-view></router-view>
	<button @click="test" > change</button>
	<br>
	
	<Son ref="son" @callshow="show"></Son>
	
	</div>
</template>

<script>
	
import Vue from 'vue'

import Son from "./Son.vue"
import { mapState } from 'vuex'
import { mapMutations } from 'vuex'
import { mapActions } from 'vuex'
import { createNamespacedHelpers } from 'vuex'

//const { mapStateChangeConrol,mapStateChangeConrol  }

export default {
	components:{
		Son
	},
	data: function() {
		return {
			school: "shuyang from parent",
		
		}
		
	},
	computed:{
		...mapState({
			count:state => state.count,
			long: state => state.long,
			}),
		...mapState('changeControl', {
			sidebar: state => state.sidebar,
			device: state => state.device
		})
	
	},
	methods:{
		bshow(){
			this.$router.push("/bar")
			
		},
		show(info){
		//	this.changeDevice({type: 'changeDevice', device: 'computer'})
			//console.log(school)
			console.log(info)
			//console.log(this.$store.state.changeControl.sidebar)
		},
		...mapMutations([
			'INCREMENT', 'trippleLong'
		]),
		...mapMutations('changeControl',['changeSidebar','changeDevice']) ,
		...mapActions([
			'trippleLong'
		]),
		test(){
			this.trippleLong({
				type:'trippleLong',
				number: 2
			})
			console.log(this.long)
			

		}
	}
}	
	
</script>

<style>
</style>
