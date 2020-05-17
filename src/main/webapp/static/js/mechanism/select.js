let vm = new Vue({
    el: "#main-container",
    data: function () {
        return {
            setting: {
                //ztree可以通过设置简单数组格式(一维数组)方式来传递节点数组，自动遍历组装成树结构
                data: {//ztree的setting对象的data属性
                    key: {//数据的key设置
                        // title:'title'	设置标题属性
                    },
                    simpleData: {//简单数组格式设置
                        enable: true,//开启简单一维数组格式 必须要有id和pId的对应关系
                        pIdKey: "parentId"    //指定office的父id属性名
                    }
                },
                callback: {
                    onClick: this.onClick   //点击回调函数
                }
            },
            area:{}
        }
    },
    methods: {
        initTree:function () {

            axios({
                url:'manager/area/selectAll',
            }).then( resp =>{
                let nodes = resp.data.obj;
                //动态生成一个根节点
                nodes[nodes.length]={id:0,name:'区域列表',open:true};
                let zTree = $.fn.zTree.init($('#pullDownTreeone'), this.setting, nodes);
            } ).catch( error =>{
                console.log(error.message)
            } )
        },
        /*
        event:事件对象
        treeId:树id  可以通过getZTreeObj()获取到树对象
        treeNode：当前操作的节点
        * */
        onClick:function (event,treeId,treeNode) {
            // console.log(treeNode);
            this.title = treeNode.name;//给页面的显示选中的节点名赋值
            // this.params.pid=treeNode.id;//给查询条件赋值
            //点击操作后，需要给父layer传递参数  父区域名、新的父区id
             parent.layer.obj=treeNode;//将新节点赋值给父layer的obj
            let frameIndex = parent.layer.getFrameIndex(window.name);
            parent.layer.close(frameIndex);
        }


    },
    created: function () {//vue对象创建后执行

    },
    mounted:function () {
        this.initTree();
    }


})
