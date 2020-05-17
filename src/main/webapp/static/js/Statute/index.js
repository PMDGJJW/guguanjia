let vm = new Vue({
    el: '.main-content',
    data: {
        ueditorConfig:{
            // ueditor.config.js->VueUeditorWrap 覆盖为"/static/UEditor/"  -> ueditorConfig覆盖为 /day63/ueditor_demo/
            UEDITOR_HOME_URL:"static/ueditor/",  //前端默认起始路径
            serverUrl: 'ueditor' ,    //服务器统一请求接口
            maximumWords:500000
        },
        status:{
            sttype:0
        },
        pageInfo: {
            pageNum: 1,
            pageSize: 5,
        },
        sta:{
            description:'',
            type:'',
            pubDate:'',
            stemFrom:'',
            title: ''
        },
        flag:false
    },
    methods: {
        selectPage(pageNum,pageSize,type){
            axios({
                url: `manager/Statues/selectPage/${pageNum}/${pageSize}/${type}`
            }).then(reps => {
                this.pageInfo=reps.data.obj;
            }).catch(error => {

            });
        },
            toUpdate(obj) {
                layer.obj=obj;
                layer.open({
                    type: 2,    //弹出  frame 窗口
                    title: '更新法律法规',
                    area: ['85%', '85%'],  //宽,高   比例是占据父窗口的比例
                    content: 'manager/Statues/toUpdate',
                })
            },
        doDelete(id){
            let _this = this;
            layer.confirm('是否删除该记录？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                axios({
                    url: 'manager/Statues/doDelete',
                    method: 'post',
                    data: {
                        id:id,
                        delFlag:"1"
                    }
                }).then(resp => {
                    if (resp.data.success) {
                        layer.msg(resp.data.msg, {icon: 1});
                        _this.selectPage(1,_this.pageInfo.pageSize,0);
                    }
                    else {
                        layer.msg(resp.data.msg, {icon: 1})
                    }
                }).catch(error => {
                    layer.msg(error.message)
                });

            });
        },
        doInsert(){
            axios({
                url:'manager/Statues/doInsert',
                data:this.sta,
                method: 'post'
            }).then( resp=>{
                if (resp.data.success) {
                   this.flag=false;
                   layer.msg(resp.data.msg);
                   this.selectPage(1,this.pageInfo.pageSize,0)
                }
                else {
                   layer.msg(resp.data.msg);
                    this.flag=false
                }
            }).catch( error=>{
                layer.msg(error.message)
            })
        },
        activeChange(){
            this.flag =! this.flag
        }
    },
    components:{
        VueUeditorWrap
    },
    created() {
        this.selectPage(1,this.pageInfo.pageSize,this.status.sttype)
    }

})
