import VueRouter from 'vue-router'
import Vue from "vue"
import Demo from "./components/Demo"
import Server from "./components/Server"
import App from "./App.vue"
import Hello from "./components/hello"
import About from "./components/about"
import Detail from "./components/detail"

Vue.use(VueRouter)


const Foo = { template: '<div>foo</div>' }
const Bar = { template: '<div>bar</div>' }

const routes= [

	   { path: '/main', component: App , name: 'Main'},
	{path: "/hello", component:  Hello, name: "hello", children: [{
		path: "detail" , component: Detail
		}]},
	{path: "/about", components: { north: About}, name: "About"},
	{path:"/home", redirect:{name: "About"}, name: "Home"},

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
