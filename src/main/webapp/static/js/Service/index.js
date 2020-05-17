let vm = new Vue({
    el: '.main-content',
    data: {
        service:{},
        pageInfo:{
            pageNum:1,
            pageSize:5
        }
    },
    methods: {
        selectPage(pageNum,pageSize) {

            axios({
                url: `manager/Service/selectPage/${pageNum}/${pageSize}`,
                method: 'post',
            }).then(reps => {
                this.pageInfo = reps.data.obj
            }).catch(error => {
                // console.log(error.message);
            })
        },
        toUpdate(obj) {
            layer.obj=obj;
            layer.open({
                type: 2,    //弹出  frame 窗口
                title: '更新服务',
                area: ['80%', '80%'],  //宽,高   比例是占据父窗口的比例
                content: 'manager/Service/toUpdate',
                // end: function () {
                //     if(layer.flag) {//更新失败
                //         console.log(this);
                //         this.selectPage(1, this.pageInfo.pageSize);
                //     }
                // }
            })
        },
        toDetail(obj) {
            layer.obj=obj;
            layer.open({
                type: 2,    //弹出  frame 窗口
                title: '更新服务',
                area: ['80%', '80%'],  //宽,高   比例是占据父窗口的比例
                content: 'manager/Service/toDetail',
                end: function () {

                }
            })
        }
    },
    created(){
        this.selectPage(this.pageInfo.pageNum,this.pageInfo.pageSize)
    }
})
