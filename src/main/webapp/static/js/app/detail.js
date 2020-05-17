let vm = new Vue({
    el:'.main-content',
    data: {
        detail:{},
        AppId:null
        },
    methods: {
        doDetail(){
           axios({
               url:'manager/app/doDetail',
               method:'post',
               data:{
                   id:this.AppId
               }
           }).then(resp =>{
               if (resp.data.success==false) {
                   layer.msg('网络开小差了');
                   this.selectPage(1, this.pageInfo.pageSize)
               }
               else {
                   this.detail=resp.data.obj
               }
           } ).catch(error =>{
               layer.msg(error.message)
           })
        }
    },
    created(){
        this.AppId = parent.layer.obj;
        this.doDetail();
    }
})
