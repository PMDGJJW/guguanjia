let vm = new Vue({
    el: '.main-content',
    data(){
        return{
        isShow:false,
        setting:{
            data:{
                key:{
                    title: 'title',
                },
                simpleData: {
                    enable:true ,//设置节点数组使用一维数组格式
                    pIdKey:'parentId'  //设置父id属性名
                }
            },
            callback:{
                onClick:this.onClick,
                beforeRemove:this.beforeRemove,
                beforeEditName:this.beforeEditName//在进入编辑按钮之前的回调
            },
            edit:{
                /*必须引入all或者  edit的文件*/
                enable:true,
                renameTitle:'修改区域',
                removeTitle:'删除区域',
            },
            view:{
                addHoverDom:this.addHoverDom,
                removeHoverDom:this.removeHoverDom
            }

        },
        nodes:'',
        flag:false,
        name: '',
        pageInfo:{
            pageNum:1,
            pageSize:5
        },
        params:{
            areaId:'',
            name:''
        },

        }
    },
    methods: {
        toUpdate(obj) {
            layer.obj=obj;
            layer.open({
                type: 2,    //弹出  frame 窗口
                title: '更新区域',
                area: ['90%', '90%'],  //宽,高   比例是占据父窗口的比例
                content: 'manager/office/toUpdate',
                end:  () => {
                    if(layer.flag){//更新成功，刷新树
                        this.initTree();
                    }
                }
            })
        },
        beforeEditName:function (treeId, treeNode) {
            //自定义修改
            console.log(treeNode);
            //获取parentName信息
            this.toUpdate(treeNode);
            return false;
        },
        beforeRemove: function (treeId, treeNode) {
            console.log(treeNode);
            return false;
        },
        addHoverDom:function (treeId, treeNode) {//移动到节点动态添加按钮逻辑
            /*
            1.参考生成修改按钮规则进行动态生成添加节点
            <span class="button edit" id="treeMenu_2_edit" title="rename" treenode_edit=""></span>
            2.绑定点击事件
             */
            // console.log(treeNode);
            let $a = $(`#${treeNode.tId}_a`);      //a连接对象
            let $add = $(`#${treeNode.tId}_add`);// 添加按钮对应的span对象
            if($add.length>0){
                return;//已经存在span，不再创建
            }
            //创建添加节点
            $add = $(`<span class="button add" id="${treeNode.tId}_add" title="insert" ></span>`);
            $a.append($add);//添加到a连接节点

            $add.on('click',this.addClick);


        },
        removeHoverDom: function (treeId, treeNode) {//移动到节点动态移除按钮逻辑

            //取消绑定动态绑定的事件
            //删除动态生成的节点
            // console.log(treeNode);
            $(`#${treeNode.tId}_add`).unbind().remove();


        },
        addClick:function (event) {
            event.stopPropagation();
            //会产生事件冒泡
            console.log("添加操作.....");
            //TODO 完成弹出添加子窗口并实现添加处理逻辑
        },
        selectAll:function(){//全部查询
            this.params={};
            this.selectPage(1,this.pageInfo.pageSize)
        },
        selectPage( pageNum,pageSize){
            axios({
                url:`manager/office/selectPage/${pageNum}/${pageSize}`,
                data:this.params,
                method:'post'
            }).then(resp =>{
                this.pageInfo = resp.data.obj;
                console.log(resp.data.obj);
            }).catch( error=>{
                layer.msg=error.message
            })
        },
        onClick(event, treeId, treeNode) {
            // console.log(event);
            // console.log(treeId);
            this.params.name=treeNode.name;
            this.selectPage(1,this.pageInfo.pageSize)


        },
        initTree() {
            axios({
                url:'manager/office/selectZtree',
                data:this.params,
                method:'post',
            }).then( resp =>{
                this.nodes = resp.data.obj;
                //动态生成一个根节点
                this.nodes[this.nodes.length]={id:0,name:'机构列表',open:false};
                let zTree = $.fn.zTree.init($('#pullDownTreeone'), this.setting, this.nodes);
            } ).catch( error =>{
                console.log(error.message)
            } )

        },
        doDelete(id){
            let _this = this;
            layer.confirm('是否删除该记录？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                axios({
                    url: `manager/office/doDelete/${id}`,
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

    },
    created() {
        this.selectPage(1,this.pageInfo.pageSize)
    } , mounted(){
        this.initTree()
    }
})
