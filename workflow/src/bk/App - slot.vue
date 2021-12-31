<template>
	
	<div> 
	<BaseLayout >

          <template v-slot="{user}">
			      this is  {{ user.lastname}}
		  </template>
			      
		<template #footer>
			this is footer
		</template>
	
	</BaseLayout>
	<button @click="show">show</button>
	
	<div id="dash"/> 

	</div>
</template>

<script>
	
import Vue from 'vue'
import BaseLayout from './components/BaseLayout.vue'
import Son from "./Son.vue"

Vue.component('An', {
  render: function (createElement) {
    return createElement(
      'h' + this.level,   // 标签名称
      this.$slots.default // 子节点数组
    )
  },
  props: {
    level: {
      type: String,
      required: true
    }
  }
})

Vue.component("button-counter",{
	template: '<button v-on:click="count++"> {{ post }} You  clicked <slot> 44444</slot> me {{ count }} times.</button>',
	props: ['post'],
	data: function(){
		return {
			count: 0
		}
	}
})

var Hello = {
			template:` <div>
			                 <h1> hello </h1>  
						   <slot> </slot>
						</div>				   `
			}

export default {
	components:{
		BaseLayout,
		Hello,
		Son
	},
	data: function() {
		return {
			school: "shuyang",
			count : 100
		}
		
	},
	methods:{
		show(){
			let dash = document.getElementById("dash")
			let show = document.createElement('div')
			show.setAttribute('id', 'show')
			dash.appendChild(show)
			
			new Vue({
				el: "#show",
				render: h =>{
					return h(
						Son,
						{ 
							props: {
								place: 'suzhou',
								},
							scopedSlots: {
								default : () =>h('div', "xxxxxxxxx") ,
								special : ({test})=>h('span', test)
							}
						}
					
					)
				}
			}

			)
		}
	}
}	
	
</script>

<style>
</style>
