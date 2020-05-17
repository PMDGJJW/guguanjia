let vm = new Vue({
    el: '.main-content',
    data(){
        return{

        pageInfo:{
            pageNum:0,
            pageSize:5
        },
        params:{
            id:"",
            name:''
        },
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
                    //进入修改之前的事件回调  用于自定义修改事件行为
                    beforeEditName:this.beforeEditName
                },
                edit:{
                    /*必须引入all或者  edit的文件*/
                    enable:true,
                    removeTitle:'删除区域',
                    renameTitle:'修改区域'
                }
            },
    }
    }
    ,
    methods: {

        selectPage(pageNum,pageSize){
            axios({
                url: `manager/area/selectPage/${pageNum}/${pageSize}`,
                data:{
                    id:this.params.id,
                    name:this.params.name
                },
                method:'post'

            }).then( resp=>{
                this.pageInfo=resp.data.obj;
                console.log(resp.data.obj);
            }).catch( error=>{

            })
        },
        selectPageByname(){
            this.selectPage(1,this.pageInfo.pageSize)
            this.params={
                id:"",
                name:''
            }

        },
        toUpdate(obj) {
            layer.obj=obj;
            layer.open({
                type: 2,    //弹出  frame 窗口
                title: '更新区域',
                area: ['85%', '85%'],  //宽,高   比例是占据父窗口的比例
                content: 'manager/area/toUpdate',
                end:  () => {
                    if(layer.flag){//更新成功，刷新树
                        this.initTree();
                    }
                }
            })
        },
        initTree() {
            axios({
                url:'manager/area/selectAll',
            }).then( resp =>{
                this.nodes = resp.data.obj;
                //动态生成一个根节点
                this.nodes[this.nodes.length]={id:0,name:'区域列表',open:true};
                let zTree = $.fn.zTree.init($('#pullDownTreeone'), this.setting, this.nodes);
            } ).catch( error =>{
                console.log(error.message)
            } )

        },
        onClick(event, treeId, treeNode) {
            this.params.id=treeNode.id;
            this.selectPage(1,this.pageInfo.pageSize)
        },
        beforeEditName:function (treeId, treeNode) {
            //自定义修改
            // console.log(treeNode);
            //获取parentName信息
            axios({
                url:'manager/area/selectBySubId',
                params:{sid:treeNode.id}
            }).then(response=>{
                this.toUpdate(response.data.obj);
            });
            //阻止进入默认的编辑行为
            return false;
        },
        doDown(){
            location.href=('manager/area/doDown')

        },
        upLoad(event) {
            let file = event.target.files[0];
            let formData = new FormData();
            formData.append("file",file);
            axios({
                url: 'manager/area/upLoad',
                data: formData,
                method: 'post',
                headers:{"content-type":"multipart/form-data"}
            }).then( resp =>{
                if (resp.data.success){
                    layer.msg(resp.data.msg);
                    this.initTree();
                    this.selectPage(1, this.pageInfo.pageSize);
                }
            }).catch( error =>{
                layer.msg(error.message)
            })
        }
    },
    created() {
        this.selectPage(1,this.pageInfo.pageSize)
    }
    , mounted(){
        this.initTree()
    }
})
