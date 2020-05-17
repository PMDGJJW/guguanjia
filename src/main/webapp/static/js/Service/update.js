let vm = new Vue({
    el: '.main-content',
    data: {
        service:{},
    },
    methods: {
        doUpdate() {
            let flag = false;
            axios({
                url: 'manager/Service/doUpdate',
                method: 'post',
                data: this.service
            }).then(resp => {
                if (resp.data.success) {
                    //关闭当前窗口需要先获取到当前窗口索引值
                    let index = parent.layer.getFrameIndex(window.name);
                    flag = true;
                    parent.layer.flag = flag;
                    // 再通过父窗口layer关闭当前窗口
                    parent.layer.close(index);
                    //在父窗口中提示
                    parent.layer.msg(resp.data.msg);
                    this.service ={};
                }
                else {
                    layer.msg(resp.data.msg);
                    flag = false;
                    parent.layer.flag = flag;
                }
            }).catch(error => {
                console.log(error.message);
            })
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
    created() {
        this.service=parent.layer.obj
    }
})
