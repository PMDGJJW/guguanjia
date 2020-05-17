let vm = new Vue({
    el: '.main-content',
    data: {
        msg:'<h2><img src="http://img.baidu.com/hi/jx2/j_0003.gif"/>Vue + UEditor + v-model双向绑定</h2>',
        ueditorConfig:{//自定义VueUeditorWrap配置项
            // ueditor.config.js->VueUeditorWrap 覆盖为"/static/UEditor/"  -> ueditorConfig覆盖为 /day63/ueditor_demo/
            UEDITOR_HOME_URL:"static/ueditor/",  //前端默认起始路径
            serverUrl: 'ueditor' ,    //服务器统一请求接口
            maximumWords:500000
        },
        status:{
            type:0
        },
        sta:{}

    },
    methods: {
        doUpdate(){
            axios({
                url:'manager/Statues/doUpdate',
                method:'post',
                data:this.sta
            }).then( resp=>{
                if (resp.data.success){
                    //关闭当前窗口需要先获取到当前窗口索引值
                    let index = parent.layer.getFrameIndex(window.name);
                    // 再通过父窗口layer关闭当前窗口
                    parent.layer.close(index);
                    //在父窗口中提示
                    parent.layer.msg(resp.data.msg);
                }
                else {
                    layer.msg(resp.data.msg);
                }
            }).catch( error=>{
                layer.msg(resp.data.msg);
            })
        },
        doClose(){
            //关闭当前窗口需要先获取到当前窗口索引值
            let index = parent.layer.getFrameIndex(window.name);
            // 再通过父窗口layer关闭当前窗口
            parent.layer.close(index);
        }
    },
    components:{
       VueUeditorWrap
    },
    created() {
        console.log(parent.layer.obj);
        this.sta=parent.layer.obj
    }

})
