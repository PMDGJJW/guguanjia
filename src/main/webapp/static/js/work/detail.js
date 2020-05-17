let vm = new Vue({
    el: '.main-content',
    data: {
        work: '',
        result:{

        }
    },
    methods: {
        selectDetail(){
            axios({
                url:`manager/Work/selectDetail/${this.work.id}`,
            }).then(resp=>{
                this.result=resp.data;
            }).catch(error=>{

            })
        }
    },
    created() {
        this.work = parent.layer.obj;
        this.selectDetail()
    }
})
