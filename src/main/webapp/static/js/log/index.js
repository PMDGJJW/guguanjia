let vm = new Vue({
    el: '.main-content',
    data: {
        pageInfo:{
            pageNum:1,
            pageSize:5
        },
        params:{
            type:''
        }
    },
    methods: {
        selectPage(pageNum,pageSize){
            axios({
                url:`manager/syslog/selectPage/${pageNum}/${pageSize}`,
                params:this.params,
            }).then(resp=>{
                this.pageInfo = resp.data.obj;
                console.log(resp.data)
            }).catch(error=>{
                console.log(error.message);
            })
        }
    },
    created() {
        this.selectPage(1,this.pageInfo.pageSize)
    },
    mounted(){
        $("#chosen-select").chosen({width: "40%",search_contains: true});
    }
})
