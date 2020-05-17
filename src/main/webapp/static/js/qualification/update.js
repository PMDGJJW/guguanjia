let vm = new Vue({
    el: '.main-content',
    data: {
        qualification:{}
    },
    methods: {
        doUpdate(check){
            this.qualification.check=check;
            axios({
                url:'manager/qualification/doUpdate',
                method:'post',
                data:this.qualification
            }).then( reps =>{
                if (reps.data.success){
                    //关闭当前窗口需要先获取到当前窗口索引值
                    let index = parent.layer.getFrameIndex(window.name);
                    // 再通过父窗口layer关闭当前窗口
                    parent.layer.close(index);
                    //在父窗口中提示
                    parent.layer.msg(response.data.msg);
                }
                else {
                    layer.msg(response.data.msg);
                }
            }).catch( error =>{
                layer.msg=error.message
            })
        },
    },
    created() {
        this.qualification = parent.layer.obj;
    }
})
