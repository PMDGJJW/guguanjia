let vm = new Vue({
    el: '.main-content',
    data: {
        user:{}
    },
    methods: {
        doDetail(id){
            axios({
                url:`manager/user/selectDetail/${id}`,
            }).then( resp=>{
                this.user=resp.data.obj[0];
                console.log(resp.data.obj[0]);
            }).catch(error=>{
                layer.msg(error.message)
            })
        }
    },
    created() {
       this.doDetail(parent.layer.obj)
    }
})
