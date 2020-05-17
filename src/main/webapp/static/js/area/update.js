let vm = new Vue({
    el: '.main-content',
    data() {
        return {
            area: {

            }
        }
    },
    methods: {
        toModules:function () {
            layer.open({
                type:2,
                content:'manager/area/toModules',
                title:'选择图标',
                area:['100%','80%'],
                end:() => {//select页关闭后的回调操作  需要将页面传递过来的参数获取，赋值给当前节点
                    this.area.icon=layer.icon;//给area的图标赋值

                }
            })
        },
        toSelect:function () {

            layer.open({
                type:2,
                content:'manager/area/toSelect',
                title:'选择父级区域',
                area:['100%','100%'],
                end:() => {//select页关闭后的回调操作  需要将页面传递过来的参数获取，赋值给当前节点
                    console.log(layer.obj);
                    if(layer.obj!=undefined&&layer.obj.id!=this.area.parentId){
                        //设置新的parent_ids 和parent_id 和parentName
                        this.area.parentName = layer.obj.name;
                        this.area.parentId = layer.obj.id;
                        // 以广州更新到 重庆为例   ：  重庆的parentIds+重庆的id+','
                        this.area.parentIds = layer.obj.parentIds+layer.obj.id+',';
                    }
                }
            })
        },
        doUpdate(){
            axios({
                url:'manager/area/doUpdate',
                data:this.area,
                method:'post'
            }).then( resp=>{
                if (resp.data.success){
                    //关闭当前窗口需要先获取到当前窗口索引值
                    let index = parent.layer.getFrameIndex(window.name);
                    parent.layer.flag = true;//成功更新标记
                    // 再通过父窗口layer关闭当前窗口
                    parent.layer.close(index);
                    //在父窗口中提示
                    parent.layer.msg(resp.data.msg);
                }
                else {
                    parent.layer.flag = false;//成功更新标记
                    layer.msg(resp.data.msg);
                }
            }).catch(error =>{
                parent.layer.flag = false;//成功更新标记
                layer.msg(error.message);
            })
        },
        doClose(){
            //关闭当前窗口需要先获取到当前窗口索引值
            let index = parent.layer.getFrameIndex(window.name);
            // 再通过父窗口layer关闭当前窗口
            parent.layer.close(index);
        }
    },
    created() {
        this.area=parent.layer.obj;
    }
})
