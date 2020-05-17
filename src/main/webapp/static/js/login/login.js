let vm = new Vue({
    el: '.main-content',
    data: {
        path:'',
        params:{
            name:'',
            passWord:'',
            code:''
        }
    },
    methods: {
        refresh(){
            this.path="getCode.jpg?"+new Date();
        },
        doLogin(){
            axios({
                url:'main/doLogin',
                data:this.params,
                method:'post'
            }).then( resp=>{
                if (resp.data.success){
                    layer.msg(resp.data.msg);
                    location.href='main/toIndex';
                    sessionStorage.setItem("loginUser",JSON.stringify(resp.data.obj.loginUser));
                    sessionStorage.setItem("userResource",JSON.stringify(resp.data.obj.userResource));
                }
                else {
                    this.params.password='';
                    layer.msg(resp.data.msg)
                }

            }).catch( error=>{
                layer.msg(error.message)
            })
        }
    },
    created() {
        this.path="getCode.jpg?"+new Date();
    }
})
