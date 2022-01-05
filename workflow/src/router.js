import VueRouter from 'vue-router'
import Vue from "vue"
import Demo from "./components/Demo"
import Server from "./components/Server"
import App from "./App.vue"

Vue.use(VueRouter)


const Foo = { template: '<div>foo</div>' }
const Bar = { template: '<div>bar</div>' }

const routes= [

	   { path: '/main', component: App , name: 'Main'},


	   { path:'/demo/:id', component: Demo, name: "Demo",
	   		children:[
				{ path: 'foo', component: Foo , name: 'Foo'},
			]}
]


const router = new VueRouter({
	routes
})
router.beforeEach((to, from, next) => {

        next();
})

export default router;
