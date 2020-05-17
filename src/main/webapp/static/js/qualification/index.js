let vm = new Vue({
    el: '.main-content',
    data: {
        pageInfo:{
            pageNum:1,
            pageSize:5
        },
        condition:{
            startDate:'',
            endDate:'',
            check:'',
            type:''//设置默认值
        }
    },
    methods: {
        selectPage(pageNum,pageSize){
            axios({
                url:`manager/qualification/selectPage/${pageNum}/${pageSize}`,
                params:this.condition
            }).then( resp =>{
                this.pageInfo=resp.data.obj
            }).catch( error =>{
                layer.msg=error.message
            } )
        },
        selectAll(pageNum,pageSize){
           this.condition={
                    startDate:'',
                    endDate:'',
                    check:'',
                    type:''//设置默认值
            }
            this.selectPage(pageNum,pageSize)
        },
        toUpdate(obj){
            layer.obj=obj;
            layer.open({
                type:2,    //弹出  frame 窗口
                title:'更新app',
                area:['80%','80%'],  //宽,高   比例是占据父窗口的比例
                content:'manager/qualification/toUpdate',
            })
        }
    },
    created() {
        this.selectPage(1,this.pageInfo.pageSize)
    }
})
