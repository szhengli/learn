<template>

	<div>
<meta name="referrer" content="no-referrer">
	<button @click="bshow" > test</button>
    <transition name="slide-right">
	<router-view></router-view>
    </transition>
	
	<img src="http://pics3.baidu.com/feed/5366d0160924ab187b9a0c04353afdc77a890bbb.jpeg?token=39566d75c8891676fa9882b96290b920"> 
	<button @click="show" > change</button>
    <button @click="sub" > sub</button>
	<br>
.............. <br>





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
		Son,
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

	  sub(){
      this.$router.push({name:"Server"})
    },
		bshow(){

			this.$router.push({name:"Demo", params:{id: 'james'}})


		},
		show(info){
		//	this.changeDevice({type: 'changeDevice', device: 'computer'})
			//console.log(school)
      this.$router.push( "/demo/james/foo")
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
