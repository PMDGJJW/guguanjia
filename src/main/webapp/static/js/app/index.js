let vm = new Vue({
    el:'.main-content',
    data:{
        pageInfo: {
            pageNum: 1,
            pageSize: 5
        },
        app: {},
        active:false
    },
    methods:{
        selectPage(pageNum, pageSize) {
            axios({
                url: `manager/app/selectPage/${pageNum}/${pageSize}`,
            }).then(resp => {
                this.pageInfo = resp.data.obj;
            })
                .catch(error => {
                    console.log(error);
                })

        },
        toUpdate(obj) {
            layer.obj=obj;
            layer.open({
                type:2,    //弹出  frame 窗口
                title:'更新app',
                area:['80%','80%'],  //宽,高   比例是占据父窗口的比例
                content:'manager/app/toUpdate',
            })
        },
        doInsert(){
            axios({
                url: 'manager/app/doInsert',
                method: 'post',
                data: this.app
            }).then(resp => {
                if (resp.data.success){
                    layer.msg(resp.data.msg);
                    this.clear();
                    this.selectPage(1, this.pageInfo.pageSize);
                    this.active=false;
                }
                else {
                    layer.msg(resp.data.msg);
                    this.active=true;
                }
            }).catch(function (error) {
                layer.msg(error.msg)
            });
        },
        doDelete(id){
            let _this = this;
            layer.confirm('是否删除该记录？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                axios({
                    url: 'manager/app/doUpdate',
                    method: 'post',
                    data: {
                        id:id,
                        delFlag:"1"
                    }
                }).then(resp => {
                    if (resp.data.success) {
                        layer.msg(resp.data.msg, {icon: 1});
                        _this.selectPage(1,_this.pageInfo.pageSize);
                    }
                    else {
                        layer.msg(resp.data.msg, {icon: 1})
                    }
                }).catch(error => {
                    layer.msg(error.message)
                });

            });
        },
        clear:function () {
            this.app={};//清空属性数据
        },
        activeChange(){
            this.active = !this.active
        },
        doDetail(id) {
            layer.obj=id;
            layer.open({
                type: 2,    //弹出  frame 窗口
                title: 'App详情',
                area: ['80%', '80%'],  //宽,高   比例是占据父窗口的比例
                content: 'manager/app/toDetail',
                end: function () {
                }
            })
        }
    },
    created(){
        this.selectPage(this.pageInfo.pageNum,this.pageInfo.pageSize)
    }
})
