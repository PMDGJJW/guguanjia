let vm = new Vue({
    el: '.main-content',
    data: {
        app:{},
    },
    methods: {
        doUpdate(){
            axios({
                url: 'manager/app/doUpdate',
                data: this.app,
                method: 'put'
            }).then(res => {
                let flag = false;
                if (res.data.success) {
                    //关闭当前窗口需要先获取到当前窗口索引值
                    let index = parent.layer.getFrameIndex(window.name);

                    //绑定成功标记
                    flag = true;
                    parent.layer.flag = flag;
                    // 再通过父窗口layer关闭当前窗口
                    parent.layer.close(index);
                    //在父窗口中提示
                    parent.layer.msg(res.data.msg);
                }
                else {
                    layer.msg(res.data.msg);
                    flag = false;
                    parent.layer.flag = flag;
                }
            }).catch(error => {
                layer.msg(error.message);
            });
        },
        doClose(){
            //关闭当前窗口需要先获取到当前窗口索引值
            let index = parent.layer.getFrameIndex(window.name);
            //绑定成功标记
             let flag = false;
            parent.layer.flag = flag;
            // 再通过父窗口layer关闭当前窗口
            parent.layer.close(index);
        }
    },
    created(){
        this.app=parent.layer.obj
    }
})
